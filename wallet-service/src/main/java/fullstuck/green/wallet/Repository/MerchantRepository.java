package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Merchant findByname(String name);
}