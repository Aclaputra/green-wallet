package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.AppUser;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AccountDetailsRepository accountDetailsRepository;
    private final UserRepository userRepository;
//    Load by Email ( Due to the naming convention of AppUser in UserDetails from Spring )
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountDetails accountDetails = accountDetailsRepository.findByemail(email);
        if(accountDetails == null){
            throw new UsernameNotFoundException("Account with Email: " + email + " not found !");
        } else {
            return AppUser.builder()
                    .id(accountDetails.getId())
                    .password(accountDetails.getPassword())
                    .role(accountDetails.getRole().getName())
                    .build();
        }
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getByPhone(String phone) {
        return userRepository.findByphone(phone);
    }

    @Override
    public void userUpdateMerchant(User user) {
        user.setUpdated_at(Date.from(Instant.now()));
        userRepository.save(user);
    }

    public ProfileDetailResponse getProfileDetail(String id) {
        // ?? gabisa klo gagini 😆
        accountDetailsRepository.findAll();
        AccountDetails accountDetails = accountDetailsRepository.findById(id).get();

        return ProfileDetailResponse
                .builder()
                .accountId(accountDetails.getId())
                .name(accountDetails.getUser().getName())
                .birthDate(String.valueOf(accountDetails.getUser().getBirthDate()))
                .phoneNumber(accountDetails.getUser().getPhone())
                .role(String.valueOf(accountDetails.getRole().getName()))
                .email(accountDetails.getEmail())
                .balance(accountDetails.getPoint())
                .point(accountDetails.getPoint())
                .isVerified(accountDetails.getIsVerified())
                .updatedAt(String.valueOf(accountDetails.getUpdated_at()))
                .build();
    }
}