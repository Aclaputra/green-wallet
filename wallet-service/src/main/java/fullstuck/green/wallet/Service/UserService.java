package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.UpdateProfileRequest;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.NoSuchElementException;

public interface UserService extends UserDetailsService {
    User getUserById(String id);
    User getByPhone(String phone);
    void userUpdateMerchant(User user);
    ProfileDetailResponse getProfileDetail(String id) throws NoSuchElementException;
    void updateProfileDetail(UpdateProfileRequest request, String idToken) throws Exception;
}