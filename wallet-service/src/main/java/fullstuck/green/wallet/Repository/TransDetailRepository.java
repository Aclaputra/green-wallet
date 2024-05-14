package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.TransDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransDetailRepository extends JpaRepository<TransDetail, String> {
}