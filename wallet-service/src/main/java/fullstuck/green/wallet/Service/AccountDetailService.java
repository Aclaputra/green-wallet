package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.DataTransferObject.AccountDetailDTO;
import fullstuck.green.wallet.Model.DataTransferObject.BalanceDTO;
import fullstuck.green.wallet.Model.Entity.AccountDetails;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountDetailService {
    // For Balance, Point, or anything Account Detail related
    void updateAccountData(String email, AccountDetailDTO accountDetailDTO);
    void deleteAccount(String email);
    AccountDetails getAccountData(String email);
    void createAccount(AccountDetails accountDetails);
    void updateBalance(BalanceDTO balanceDTO);
    void updatePoints(BalanceDTO balanceDTO);
}