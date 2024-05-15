package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.Merchant;
import fullstuck.green.wallet.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    Merchant findBymerchant();
}