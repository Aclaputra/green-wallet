package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.TransactionDTO;
import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.TransDetailRepository;
import fullstuck.green.wallet.Repository.TransactionRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Service.TransactionService;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.Strings.MerchantEnum;
import fullstuck.green.wallet.Strings.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserService userService;
    private final AccountDetailsRepository accountDetailsRepository;
    private final TransactionRepository transactionRepository;
    private final TransDetailRepository transDetailRepository;

    private String getUserInfo(){
        // Return UserId based on current active user ( AppUser login )
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) auth.getPrincipal();
        return appUser.getId();
    }

    @Override
    public void addTransaction(TransactionDTO transactionDTO) {
        User user;
        Merchant merchant;
        BigDecimal curr = new BigDecimal("0.0");

        TransactionType temp = switch (transactionDTO.getType()) {
            case 0 -> TransactionType.PAYMENT_OUT;
            case 1 -> TransactionType.TRANSFER;
            case 2 -> TransactionType.PAYMENT_IN;
            case 3 -> TransactionType.TOP_UP;
            default -> null;
        };

        // getTransForm 1 == User || 2 == merchant
        if(transactionDTO.getTransFrom() == 1){
            user = userService.getUserById(getUserInfo());
            merchant = null;
            AccountDetails accountDetails = accountDetailsRepository.findByuser(getUserInfo());

            // From user ( Account Details )
            // If TransType == Payment / Transfer ( Bakal subtract, else Add )
            // && If Balance > Amount mau di subtract
            if(temp == TransactionType.PAYMENT_OUT || temp == TransactionType.TRANSFER || accountDetails.getBalance().compareTo(transactionDTO.getAmount()) < 0){
                curr = accountDetails.getBalance().subtract(transactionDTO.getAmount());
            } else if(temp == TransactionType.PAYMENT_IN || temp == TransactionType.TOP_UP){
                curr = accountDetails.getBalance().add(transactionDTO.getAmount());
            } else {
                throw new IllegalArgumentException("You don't have enough money !");
            }

        } else if(transactionDTO.getTransFrom() == 2){
            user = userService.getUserById(getUserInfo());
            merchant = user.getMerchant();

            if(merchant == null){
                throw new NoSuchElementException("No merchant Id found !");
            } else {
                // Sama kayak di atas tapi ini pake merchant
                if(temp == TransactionType.PAYMENT_OUT || temp == TransactionType.TRANSFER || merchant.getBalance().compareTo(transactionDTO.getAmount()) < 0){
                    curr = merchant.getBalance().subtract(transactionDTO.getAmount());
                } else if(temp == TransactionType.PAYMENT_IN || temp == TransactionType.TOP_UP){
                    curr = merchant.getBalance().add(transactionDTO.getAmount());
                } else {
                    throw new IllegalArgumentException("You don't have enough money !");
                }

                user = null;
            }
        } else {
            throw new IllegalArgumentException("Illegal !");
        }

        TransDetail transDetail = TransDetail.builder()
                .type(temp)
                .target_id(transactionDTO.getTarget())
                .source_id(getUserInfo())
                .description(transactionDTO.getDescription())
                .amount(transactionDTO.getAmount())
                .curr_balance(curr)
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .build();
        transDetailRepository.save(transDetail);

        Transaction transaction = Transaction.builder()
                .user(user)
                .merchant(merchant)
                .transDetail(transDetail)
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAllByuser(getUserInfo());
    }

    @Override
    public Transaction getTransactionById(String id) {
        return transactionRepository.findById(id).get();
    }
}
