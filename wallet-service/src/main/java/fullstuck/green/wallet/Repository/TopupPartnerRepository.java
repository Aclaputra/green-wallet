package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.TopupPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopupPartnerRepository extends JpaRepository<TopupPartner, String> {
    TopupPartner findByname(String name);
}