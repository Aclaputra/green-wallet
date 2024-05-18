package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, String> {
    PasswordResetToken findBytoken(String token);
    PasswordResetToken findByemail(String email);
}