package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUserById(String id);
    ProfileDetailResponse getProfileDetail(String id);
}