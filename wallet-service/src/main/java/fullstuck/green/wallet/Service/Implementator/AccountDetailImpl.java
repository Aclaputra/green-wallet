package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountDetailImpl implements AccountDetailService {
    private final AccountDetailsRepository accountDetailsRepository;

    @Override
    public void updateAccountData(AccountDetails accountDetails) {

    }

    @Override
    public void deleteMember(String email) {
        accountDetailsRepository.deleteByemail(email);
    }

    @Override
    public Optional<AccountDetails> getAccountData(String email) {
        return accountDetailsRepository.findByemail(email);
    }

    @Override
    public void createAccount(AccountDetails accountDetails) {
        accountDetailsRepository.save(accountDetails);
    }
}
