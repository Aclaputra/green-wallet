package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.AccountDetailDTO;
import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountDetailService {
    AccountDetails getAccountDetailById(String id);
    List<AccountDetails> getAccounts();
    // For Balance, Point, or anything Account Detail related
    void updateAccountData(String email, AccountDetailDTO accountDetailDTO);
    void deleteAccount(String email);
    AccountDetails getAccountData(String email);
    // Create Account di handle pas Register User ( sekalian sama user )
    void createAccount(AccountDetails account);
    void updateBalance(BalanceDTO balanceDTO);
    void updatePoints(BalanceDTO balanceDTO);
}