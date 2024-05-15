package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.AccountDetailDTO;
import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountDetailImpl implements AccountDetailService {
    private final AccountDetailsRepository accountDetailsRepository;

    @Override
    public AccountDetails getAccountDetailById(String id) throws NoSuchElementException{
        System.out.println("accoun detail by id: " + id);
        // ?? baru bisa find by id setelah get accounts aneh bin sus
        getAccounts();
        return accountDetailsRepository.findById(id).get();
//        try {
//            return accountDetailsRepository.findById(id).get();
//        } catch (NoSuchElementException e) {
//            throw new Error("Error " + e.getMessage());
//        }
    }

    @Override
    public List<AccountDetails> getAccounts() {
        return accountDetailsRepository.findAll();
    }

    @Override
    public void updateAccountData(AccountDetails accountDetails) {
        accountDetails.setUpdated_at(Date.from(Instant.now()));
        accountDetailsRepository.save(accountDetails);
    }

    @Override
    public void deleteAccount(String id) {
        AccountDetails accountDetails = accountDetailsRepository.findById(id).get();
        if(!accountDetails.getIsDeleted()){
            accountDetails.setDeleted_at(Date.from(Instant.now()));
            accountDetails.setIsDeleted(Boolean.TRUE);
            accountDetails.setUpdated_at(Date.from(Instant.now()));
            accountDetailsRepository.save(accountDetails);
        } else {
            throw new IllegalArgumentException("Already deleted !");
        }
    }

    @Override
    public AccountDetails getAccountData(User user) {
        return accountDetailsRepository.findByuser(user);
    }

    @Override
    public void createAccount(AccountDetails account) {
        accountDetailsRepository.save(account);
    }

    @Override
    public void updateBalance(String id, BigDecimal amount, int type) {
        AccountDetails accountDetails = accountDetailsRepository.findById(id).get();
        // 1 = Payment / Transfer || 2 = Topup / Recieve Payment
        if(type == 1){
            // compareTo : 0 equals parameter || 1 greater than parameter || -1 less than parameter
            if(accountDetails.getBalance().compareTo(amount) < 0){
                throw new IllegalArgumentException("You don't have enough money !");
            } else {
                accountDetails.setBalance(accountDetails.getBalance().subtract(amount));
            }
        } else if (type == 2){
            accountDetails.setBalance(accountDetails.getBalance().add(amount));
        }
        // Change updated date to now
        accountDetails.setUpdated_at(Date.from(Instant.now()));
        accountDetailsRepository.save(accountDetails);
    }

    @Override
    public void updatePoints(String id, BigDecimal amount, int type) {
        AccountDetails accountDetails = accountDetailsRepository.findById(id).get();
        // 1 = Payment / Transfer || 2 = Topup / Recieve Payment
        if(type == 1){
            // compareTo : 0 equals parameter || 1 greater than parameter || -1 less than parameter
            if(accountDetails.getPoint().compareTo(amount) < 0){
                throw new IllegalArgumentException("You don't have enough points !");
            } else {
                accountDetails.setPoint(accountDetails.getBalance().subtract(amount));
            }
        } else if (type == 2){
            accountDetails.setPoint(accountDetails.getBalance().add(amount));
        }

        accountDetails.setUpdated_at(Date.from(Instant.now()));
        accountDetailsRepository.save(accountDetails);
    }
}
