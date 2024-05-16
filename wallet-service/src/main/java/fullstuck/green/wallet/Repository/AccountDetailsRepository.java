package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, String> {

//    Optional<AccountDetails> findById(String id);
    AccountDetails findByemail(String email);
    AccountDetails findByuser(User user);
//    void deleteByemail(String email);
//    AccountDetails findByuser(String id);
}