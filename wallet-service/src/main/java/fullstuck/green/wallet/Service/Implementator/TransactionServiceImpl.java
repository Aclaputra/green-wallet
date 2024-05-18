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

import java.time.Instant;
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

        accountDetailService.updateBalance(accountDetails.getId(), req.getAmount(), 1);
        accountDetailService.updateBalance(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId(), req.getAmount(), 2);

        Transaction transaction = Transaction.builder()
                .user(user)
                .transDetail(transDetail)
                .build();

        Transaction savedTransfer = transactionRepository.save(transaction);

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

        accountDetailService.updateBalance(accountDetails.getId(), req.getAmount(), 2);

        Transaction transaction = Transaction.builder()
                .user(user)
                .transDetail(transDetail)
                .build();

        Transaction savedTopUp = transactionRepository.save(transaction);

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

            accountDetailService.updateBalance(accountDetails.getId(), req.getAmount(), 1);
            merchantService.updateBalance(merchantService.getByName(req.getDestination()).getId(), req.getAmount(), 2);
            try {
                accountDetailService.updateBalance(accountDetails.getId(), req.getAmount(), 1);
            } catch (Exception e) {
                throw new RuntimeException("Exception A: " + e);
            }
            try {
                merchantService.updateBalance(merchantService.getByName(req.getDestination()).getId(), req.getAmount(), 2);
            } catch (Exception e) {
                throw new RuntimeException("Exception B: " + e);
            }

            Transaction transaction = Transaction.builder()
                    .user(user)
                    .transDetail(transDetail)
                    .build();

            Transaction savedTransfer = transactionRepository.save(transaction);

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

        merchantService.updateBalance(merchant.getId(), req.getAmount(), 1);
        accountDetailService.updateBalance(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId(), req.getAmount(), 2);

        Transaction transaction = Transaction.builder()
                .merchant(merchant)
                .transDetail(transDetail)
                .build();

        Transaction savedTransfer = transactionRepository.save(transaction);

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

        return HistoryDetailResponse.builder()
                .id(id)
                .transType(transaction.getTransDetail().getType().toString())
                .transDate(transaction.getTransDetail().getCreated_at())
                .message(transaction.getTransDetail().getDescription())
                .target(transaction.getTransDetail().getTarget_id())
                .targetName(accountDetailService.getAccountDetailById(transaction.getTransDetail().getTarget_id()).getUser().getName())
                .source(transaction.getTransDetail().getSource_id())
                .amount(transaction.getTransDetail().getAmount())
                .currBalance(transaction.getTransDetail().getCurr_balance())
                .build();
    }

    @Override
    public Page<CustomHistoryInterface> getHistoryPerPage(Pageable pageable, String id) {
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(id);

        return transactionRepository.findAllCustomPage(accountDetails.getUser().getId(), accountDetails.getId(), pageable);
    }
}
