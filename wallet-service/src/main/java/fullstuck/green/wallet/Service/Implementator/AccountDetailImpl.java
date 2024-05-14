package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.AccountDetailDTO;
import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void updateAccountData(String email, AccountDetailDTO accountDetailDTO) {
        // No if email not found exception, karena kalo mau rubah akun perlu ada akun
        AccountDetails accountDetails = accountDetailsRepository.findByemail(email);
        if(accountDetailDTO.getEmail() != null){
            accountDetails.setEmail(accountDetailDTO.getEmail());
        } else if (accountDetailDTO.getIsVerified() != null) {
            accountDetails.setIsVerified(accountDetailDTO.getIsVerified());
        } else if (accountDetailDTO.getPassword() != null) {
            accountDetails.setPassword(accountDetailDTO.getPassword());
        } else if (accountDetailDTO.getRole() != null) {
//            Pending
//            accountDetails.setRole(Role.builder().build());
        }
    }

    @Override
    public void deleteAccount(String email) {
        AccountDetails accountDetails = accountDetailsRepository.findByemail(email);
        if(!accountDetails.getIsDeleted()){
            accountDetails.setDeleted_at(Date.from(Instant.now()));
            accountDetails.setIsDeleted(Boolean.TRUE);
            accountDetailsRepository.save(accountDetails);
        } else {
            throw new IllegalArgumentException("Already deleted !");
        }
    }

    @Override
    public AccountDetails getAccountData(String email) {
        return accountDetailsRepository.findByemail(email);
    }

    @Override
    public void createAccount(AccountDetails account) {
        accountDetailsRepository.save(account);
    }

    @Override
    public void updateBalance(BalanceDTO balanceDTO) {
        AccountDetails accountDetails = accountDetailsRepository.findByemail(balanceDTO.getData());
        // 1 = Payment / Transfer || 2 = Topup / Recieve Payment
        if(balanceDTO.getTransType() == 1){
            // compareTo : 0 equals parameter || 1 greater than parameter || -1 less than parameter
            if(accountDetails.getBalance().compareTo(balanceDTO.getAmount()) < 0){
                throw new IllegalArgumentException("You don't have enough money !");
            } else {
                accountDetails.setBalance(accountDetails.getBalance().subtract(balanceDTO.getAmount()));
            }
        } else if (balanceDTO.getTransType() == 2){
            accountDetails.setBalance(accountDetails.getBalance().add(balanceDTO.getAmount()));
        }
    }

    @Override
    public void updatePoints(BalanceDTO balanceDTO) {
        AccountDetails accountDetails = accountDetailsRepository.findByemail(balanceDTO.getData());
        // 1 = Payment / Transfer || 2 = Topup / Recieve Payment
        if(balanceDTO.getTransType() == 1){
            // compareTo : 0 equals parameter || 1 greater than parameter || -1 less than parameter
            if(accountDetails.getPoint().compareTo(balanceDTO.getAmount()) == -1){
                throw new IllegalArgumentException("You don't have enough points !");
            } else {
                accountDetails.setPoint(accountDetails.getBalance().subtract(balanceDTO.getAmount()));
            }
        } else if (balanceDTO.getTransType() == 2){
            accountDetails.setPoint(accountDetails.getBalance().add(balanceDTO.getAmount()));
        }
    }
}
