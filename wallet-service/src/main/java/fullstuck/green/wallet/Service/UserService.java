package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUserById(String id);
    User getByPhone(String phone);
    void userUpdateMerchant(User user);
}