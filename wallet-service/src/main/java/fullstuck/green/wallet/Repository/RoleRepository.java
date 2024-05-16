package fullstuck.green.wallet.Repository;

import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Strings.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(RoleEnum role);
}