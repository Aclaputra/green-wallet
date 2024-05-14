package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, String> {
    AccountDetails findByemail(String email);
    void deleteByemail(String email);
    AccountDetails findByuser(String id);
}