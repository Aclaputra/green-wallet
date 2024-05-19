package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.*;
import fullstuck.green.wallet.Repository.TransactionRepository;
import fullstuck.green.wallet.Service.*;
import fullstuck.green.wallet.Strings.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserService userService;
    private final AccountDetailService accountDetailService;
    private final TransactionRepository transactionRepository;
    private final TransDetailService transDetailService;
    private final TopupPartnerService topupPartnerService;
    private final MerchantService merchantService;

    private final static BigDecimal amount = new BigDecimal("1500.00");

    @Override
    @Transactional
    public List<Transaction> getAllTransaction(String accountIdFromToken) {
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountIdFromToken);
        User user = userService.getUserById(accountDetails.getUser().getId());
//        return transactionRepository.findAllByuser(user);
        return null;
//        return transactionRepository.findByUserId(user.getId());
    }

    @Override
    public List<CustomHistoryInterface> findAllCustom(String userIdFromToken) {
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(userIdFromToken);
        return transactionRepository.findAllCustom(accountDetails.getUser().getId(), accountDetails.getId());
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest req, String accountDetailIdToken) {
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountDetailIdToken);
        User user = userService.getUserById(accountDetails.getUser().getId());

        TransDetail transDetail = TransDetail.builder()
                .amount(req.getAmount())
                .type(TransactionType.TRANSFER.toString())
                .source_id(accountDetails.getUser().getId())
                .target_id(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId())
                .description(req.getMessage())
                .curr_balance(accountDetails.getBalance().add(req.getAmount()))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .amount(req.getAmount())
                .build();
        transDetailService.saveTransactionDetail(transDetail);

        accountDetailService.updateBalance(accountDetails.getId(), req.getAmount().add(amount), 1);
        accountDetailService.updateBalance(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId(), req.getAmount(), 2);

        Transaction transaction = Transaction.builder()
                .user(user)
                .transDetail(transDetail)
                .build();

        Transaction savedTransfer = transactionRepository.save(transaction);
        adminFeeRecord(accountDetails.getUser().getId());

        return TransferResponse.builder()
                .amount(String.valueOf(savedTransfer.getTransDetail().getAmount()))
                .description(savedTransfer.getTransDetail().getDescription())
                .build();
    }

    @Override
    @Transactional
    public TopupResponse topUp(TopUpRequest req, String accountDetailIdToken) {
        // TOPUP FROM PARTNER TO USER
        // Required : Partner ID
        // Register partner dulu, get ID

        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountDetailIdToken);
        User user = userService.getUserById(accountDetails.getUser().getId());

        TransDetail transDetail = TransDetail.builder()
                .amount(req.getAmount())
                .type(TransactionType.TOP_UP.toString())
                .source_id(topupPartnerService.getPartnerId(req.getSourceOfFundId()))
                .target_id(accountDetails.getUser().getId())
                .description(req.getMessage())
                .curr_balance(accountDetails.getBalance().add(req.getAmount()))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .build();

        topupPartnerService.updateBalance(topupPartnerService.getPartnerId(req.getSourceOfFundId()), req.getAmount());
        transDetailService.saveTransactionDetail(transDetail);

        accountDetailService.updateBalance(accountDetails.getId(), req.getAmount().subtract(amount), 2);

        Transaction transaction = Transaction.builder()
                .user(user)
                .transDetail(transDetail)
                .build();

        Transaction savedTopUp = transactionRepository.save(transaction);
        adminFeeRecord(accountDetails.getUser().getId());

        return TopupResponse.builder()
                .amount(String.valueOf(savedTopUp.getTransDetail().getAmount()))
                .via(savedTopUp.getTransDetail().getSource_id())
                .build();
    }

    @Override
    public TransferResponse payment(TransferRequest req, String accountDetailIdToken) {
        // Payment from USER >> Merchant with X name
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountDetailIdToken);
        Merchant merchant = merchantService.getByName(req.getDestination());
        User user = userService.getUserById(accountDetails.getUser().getId());

        if(merchant.getId() != null){
            TransDetail transDetail = TransDetail.builder()
                    .amount(req.getAmount())
                    .type(TransactionType.PAYMENT.toString())
                    .source_id(accountDetails.getUser().getId())
                    .target_id(merchant.getId())
                    .description(req.getMessage())
                    .curr_balance(accountDetails.getBalance().add(req.getAmount()))
                    .created_at(Date.from(Instant.now()))
                    .updated_at(Date.from(Instant.now()))
                    .amount(req.getAmount())
                    .build();
            transDetailService.saveTransactionDetail(transDetail);

            accountDetailService.updateBalance(accountDetails.getId(), req.getAmount().add(amount), 1);
            merchantService.updateBalance(merchantService.getByName(req.getDestination()).getId(), req.getAmount(), 2);

            Transaction transaction = Transaction.builder()
                    .user(user)
                    .merchant(merchant)
                    .transDetail(transDetail)
                    .build();

            Transaction savedTransfer = transactionRepository.save(transaction);
            adminFeeRecord(accountDetails.getUser().getId());
            return TransferResponse.builder()
                    .amount(String.valueOf(savedTransfer.getTransDetail().getAmount()))
                    .description(savedTransfer.getTransDetail().getDescription())
                    .build();
        } else {
            throw new NoSuchElementException("Merchant with name " + req.getDestination() + " not found !");
        }
    }

    @Override
    public TransferResponse merchantTransfer(TransferRequest req, String accountDetailIdToken) {
        // TRANSFER from MERCHANT(User) >> Any user with X phone number
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountDetailIdToken);
        Merchant merchant = userService.getUserById(accountDetails.getUser().getId()).getMerchant();

        TransDetail transDetail = TransDetail.builder()
                .amount(req.getAmount())
                .type(TransactionType.TRANSFER.toString())
                .source_id(merchant.getId())
                .target_id(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId())
                .description(req.getMessage())
                .curr_balance(accountDetails.getBalance().add(req.getAmount()))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .amount(req.getAmount())
                .build();
        transDetailService.saveTransactionDetail(transDetail);

        merchantService.updateBalance(merchant.getId(), req.getAmount().add(amount), 1);
        accountDetailService.updateBalance(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId(), req.getAmount(), 2);

        Transaction transaction = Transaction.builder()
                .merchant(merchant)
                .user(accountDetails.getUser())
                .transDetail(transDetail)
                .build();

        Transaction savedTransfer = transactionRepository.save(transaction);
        adminFeeRecord(merchant.getId());
        return TransferResponse.builder()
                .amount(String.valueOf(savedTransfer.getTransDetail().getAmount()))
                .description(savedTransfer.getTransDetail().getDescription())
                .build();
    }

    @Override
    public Transaction getById(String id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public HistoryDetailResponse getByIdSpecial(String id) {
        Transaction transaction = transactionRepository.findById(id).get();
        String name = null;
        if(transaction.getTransDetail().getType().equals("PAYMENT")) {
            name = transaction.getMerchant().getName();
        } else if(transaction.getTransDetail().getType().equals("TRANSFER")){
            name = transaction.getUser().getName();
        } else if (transaction.getTransDetail().getType().equals("TOP_UP")) {
            name = topupPartnerService.getPartnerData(transaction.getTransDetail().getSource_id()).getName();
        }
        return HistoryDetailResponse.builder()
                .id(id)
                .transType(transaction.getTransDetail().getType())
                .transDate(transaction.getTransDetail().getCreated_at())
                .message(transaction.getTransDetail().getDescription())
                .target(transaction.getTransDetail().getTarget_id())
                .targetName(name)
                .source(transaction.getTransDetail().getSource_id())
                .amount(transaction.getTransDetail().getAmount())
                .currBalance(transaction.getTransDetail().getCurr_balance())
                .build();
    }

    @Override
    public Page<CustomHistoryInterface> getHistoryPerPage(Pageable pageable, String id) {
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(id);
        return transactionRepository.findAllCustomPage(pageable, accountDetails.getUser().getId(), accountDetails.getId());
    }

    private void adminFeeRecord(String idSource){
        AccountDetails accountDetails = accountDetailService.getByEmail("admin@grn.wallet.com");
        TransDetail transDetail = TransDetail.builder()
                .amount(amount)
                .type(TransactionType.ADMIN_FEE.toString())
                .source_id(idSource)
                .target_id(accountDetails.getUser().getName())
                .description("ADMIN FEE")
                .curr_balance(accountDetails.getBalance().add(amount))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .build();
        transDetailService.saveTransactionDetail(transDetail);
        accountDetailService.updateBalance(accountDetails.getId(), amount, 2);

        Transaction transaction = Transaction.builder()
                .merchant(null)
                .user(accountDetails.getUser())
                .transDetail(transDetail)
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public List<Integer> getTransCount(String id) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        Date start = java.sql.Date.valueOf(startOfWeek);
        Date end = java.sql.Date.valueOf(endOfWeek);

        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        Date startMonth = java.sql.Date.valueOf(startOfMonth);
        Date endMonth = java.sql.Date.valueOf(endOfMonth);

        List<DailyTrans> test = transactionRepository.findAllCustomDate(start, end, id);
        List<Integer> result = new ArrayList<>();

        Integer mon = 0;
        Integer teu = 0;
        Integer wed = 0;
        Integer thu = 0;
        Integer fri = 0;
        Integer sat = 0;
        Integer sun = 0;
        Integer sum = 0;

        for (DailyTrans dailyTrans : test) {
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(0).toString()))) {
                mon = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(1).toString()))) {
                teu = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(2).toString()))) {
                wed = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(3).toString()))) {
                thu = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(4).toString()))) {
                fri = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(5).toString()))) {
                sat = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(6).toString()))) {
                sun = dailyTrans.getTotal();
                sum += dailyTrans.getTotal();
            }
        }

        result.add(mon);
        result.add(teu);
        result.add(wed);
        result.add(thu);
        result.add(fri);
        result.add(sat);
        result.add(sun);
        result.add(sum);

        Integer count = 0;
        List<DailyTrans> monthly = transactionRepository.findAllCustomDate(startMonth, endMonth, id);
        for (DailyTrans monthlyTrans : monthly) {
            count += monthlyTrans.getTotal();
        }
        result.add(count);

        return result;
    }

    @Override
    public List<List<BigDecimal>> getDailySum(String id) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        Date start = java.sql.Date.valueOf(startOfWeek);
        Date end = java.sql.Date.valueOf(endOfWeek);

        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        Date startMonth = java.sql.Date.valueOf(startOfMonth);
        Date endMonth = java.sql.Date.valueOf(endOfMonth);

        List<DailySum> test = transactionRepository.findAllCustomSum(start, end, id);
        List<List<BigDecimal>> result = new ArrayList<>();
        List<BigDecimal> inResult = new ArrayList<>();
        List<BigDecimal> outResult = new ArrayList<>();

        BigDecimal monIn = new BigDecimal("0.0");
        BigDecimal teuIn = new BigDecimal("0.0");
        BigDecimal wedIn = new BigDecimal("0.0");
        BigDecimal thuIn = new BigDecimal("0.0");
        BigDecimal friIn = new BigDecimal("0.0");
        BigDecimal satIn = new BigDecimal("0.0");
        BigDecimal sunIn = new BigDecimal("0.0");
        BigDecimal sumIn = new BigDecimal("0.0");

        BigDecimal mon = new BigDecimal("0.0");
        BigDecimal teu = new BigDecimal("0.0");
        BigDecimal wed = new BigDecimal("0.0");
        BigDecimal thu = new BigDecimal("0.0");
        BigDecimal fri = new BigDecimal("0.0");
        BigDecimal sat = new BigDecimal("0.0");
        BigDecimal sun = new BigDecimal("0.0");
        BigDecimal sumOut = new BigDecimal("0.0");

        for (DailySum dailyTrans : test) {
//            System.out.println("TYPE : " + dailyTrans.getType());
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(0).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    mon = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    monIn = dailyTrans.getTotal();
                    sumOut = sumIn.add(dailyTrans.getTotal());
                }
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(1).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    teu = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    teuIn = dailyTrans.getTotal();
                    sumIn = sumIn.add(dailyTrans.getTotal());
                }
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(2).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    wed = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    wedIn = dailyTrans.getTotal();
                    sumIn = sumIn.add(dailyTrans.getTotal());
                }
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(3).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    thu = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    thuIn = dailyTrans.getTotal();
                    sumIn = sumIn.add(dailyTrans.getTotal());
                }
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(4).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    fri = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    friIn = dailyTrans.getTotal();
                    sumIn = sumIn.add(dailyTrans.getTotal());
                }
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(5).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    sat = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    satIn = dailyTrans.getTotal();
                    sumIn = sumIn.add(dailyTrans.getTotal());
                }
            }
            if (dailyTrans.getTransDate().equals((startOfWeek.plusDays(6).toString()))) {
                if (dailyTrans.getType() == "TRANSFER" || dailyTrans.getType() == "PAYMENT") {
                    sun = dailyTrans.getTotal();
                    sumOut = sumOut.add(dailyTrans.getTotal());
                } else if(dailyTrans.getType() == "TOP_UP"){
                    sunIn = dailyTrans.getTotal();
                    sumIn = sumIn.add(dailyTrans.getTotal());
                }
            }
        }

        BigDecimal countIn = new BigDecimal("0.0");
        BigDecimal countOut = new BigDecimal("0.0");

        List<DailySum> monthly = transactionRepository.findAllCustomSum(startMonth, endMonth, id);
        for (DailySum monthlyTrans : monthly) {
            if(monthlyTrans.getType() == "TRANSFER" || monthlyTrans.getType() == "PAYMENT"){
                countOut = countOut.add(monthlyTrans.getTotal());
            } else {
                countIn = countIn.add(monthlyTrans.getTotal());
            }
        }

        inResult.add(monIn);
        inResult.add(teuIn);
        inResult.add(wedIn);
        inResult.add(thuIn);
        inResult.add(friIn);
        inResult.add(satIn);
        inResult.add(sunIn);
        inResult.add(sumIn);
        inResult.add(countIn);

        outResult.add(mon);
        outResult.add(teu);
        outResult.add(wed);
        outResult.add(thu);
        outResult.add(fri);
        outResult.add(sat);
        outResult.add(sun);
        outResult.add(sumOut);
        outResult.add(countOut);

        result.add(inResult);
        result.add(outResult);

        return result;
    }
}