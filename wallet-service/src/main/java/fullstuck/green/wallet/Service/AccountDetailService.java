package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.AccountDetailDTO;
import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountDetailService {
    AccountDetails getAccountDetailById(String id);
    List<AccountDetails> getAccounts();
    // For Balance, Point, or anything Account Detail related
    void updateAccountData(String email, AccountDetailDTO accountDetailDTO);
    void deleteAccount(String email);
    AccountDetails getAccountData(User user);
    // Create Account di handle pas Register User ( sekalian sama user )
    void createAccount(AccountDetails account);
    void updateBalance(String accountId, BigDecimal amount, int transType);
    void updatePoints(BalanceDTO balanceDTO);
}