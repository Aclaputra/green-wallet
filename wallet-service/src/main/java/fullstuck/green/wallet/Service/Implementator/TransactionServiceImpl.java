package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.TopupResponse;
import fullstuck.green.wallet.Model.Response.TransferResponse;
import fullstuck.green.wallet.Repository.TransDetailRepository;
import fullstuck.green.wallet.Repository.TransactionRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.TransactionService;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.Strings.TransactionType;
import fullstuck.green.wallet.security.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserService userService;
    private final AccountDetailService accountDetailService;
    private final TransactionRepository transactionRepository;
    private final TransDetailRepository transDetailRepository;
    private final JWTUtil jwtUtil;

    @Override
    @Transactional
    public List<Transaction> getAllTransaction(String accountIdFromToken) {
        System.out.println("Transaction token: " + accountIdFromToken);
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountIdFromToken);
        System.out.println("account detail : " + accountDetails);
        User user = userService.getUserById(accountDetails.getUser().getId());
        System.out.println("user: " + user);
        return transactionRepository.findAllByuser(user);
    }

//    @Override
//    public Transaction getTransactionById(String id) {
//        return transactionRepository.findById(id).get();
//    }


    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest req, String accountDetailIdToken) {
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountDetailIdToken);
        User user = userService.getUserById(accountDetails.getUser().getId());

        TransDetail transDetail = TransDetail.builder()
                .amount(req.getAmount())
                .type(TransactionType.TRANSFER)
                .source_id(accountDetails.getUser().getId())
                .target_id(accountDetailService.getAccountData(userService.getByPhone(req.getDestination())).getId())
                .description(req.getMessage())
                .curr_balance(accountDetails.getBalance().add(req.getAmount()))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .amount(req.getAmount())
                .build();
        transDetailRepository.save(transDetail);

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
        // Top up cuma bisa ke akun sendiri ya !

        System.out.println("Transaction token: " + accountDetailIdToken);
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(accountDetailIdToken);
        System.out.println("account detail : " + accountDetails);
        User user = userService.getUserById(accountDetails.getUser().getId());
        System.out.println("user: " + user);

        TransDetail transDetail = TransDetail.builder()
                .amount(req.getAmount())
                .type(TransactionType.TOP_UP)
                .source_id(req.getSourceOfFundId())
                .target_id(accountDetails.getUser().getId())
                .description(req.getMessage())
                .curr_balance(accountDetails.getBalance().add(req.getAmount()))
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .build();
        transDetailRepository.save(transDetail);

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
}
