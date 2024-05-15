package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.AppUser;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
//        return null;
    }
}