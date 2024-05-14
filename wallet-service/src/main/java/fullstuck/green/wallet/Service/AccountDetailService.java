package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.AccountDetails;

import java.util.Optional;

public interface AccountDetailService {
    // For Balance, Point, or anything Account Detail related
    void updateAccountData(AccountDetails accountDetails);
    void deleteMember(String email);
    Optional<AccountDetails> getAccountData(String email);
    void createAccount(AccountDetails accountDetails);
}