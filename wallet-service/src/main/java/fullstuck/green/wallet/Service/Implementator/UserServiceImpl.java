package fullstuck.green.wallet.Service.Implementator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import fullstuck.green.wallet.Config.Patcher;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.AppUser;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.UpdateProfileRequest;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.sql.Update;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
//    private final Patcher patcher;
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

    public ProfileDetailResponse getProfileDetail(String id) throws NoSuchElementException {
        try {
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
                    .profileImageUrl(accountDetails.getProfileImageUrl())
                    .balance(accountDetails.getBalance())
                    .point(accountDetails.getPoint())
                    .isVerified(accountDetails.getIsVerified())
                    .updatedAt(String.valueOf(accountDetails.getUpdated_at()))
                    .build();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Error: " + e.getMessage());
        }
    }

    @Override
    public void updateProfileDetail(UpdateProfileRequest incompleteRequest, String idToken) throws Exception {
        try {
            accountDetailsRepository.findAll();
            AccountDetails existingAccount = accountDetailsRepository.findById(incompleteRequest.getId()).get();
            UpdateProfileRequest existingUpdateProfile = UpdateProfileRequest.builder()
                    .name(existingAccount.getUser().getName())
                    .profileImageUrl(existingAccount.getProfileImageUrl())
                    .birthDate(String.valueOf(existingAccount.getUser().getBirthDate()))
                    .phoneNumber(existingAccount.getUser().getPhone())
                    .build();
            Patcher.profilePatcher(existingUpdateProfile, incompleteRequest);
            Date birthDate = DateUtils.parseDate(existingUpdateProfile.getBirthDate(),
                    new String[] {"dd-MM-yyy" }); //Change from yyyy-MM-dd to dd-MM-yyyy
            existingAccount.getUser().setName(existingUpdateProfile.getName());
            existingAccount.getUser().setPhone(existingUpdateProfile.getPhoneNumber());
            existingAccount.getUser().setUpdated_at(Date.from(Instant.now()));
            existingAccount.getUser().setBirthDate(birthDate);
            existingAccount.setProfileImageUrl(existingUpdateProfile.getProfileImageUrl());

            accountDetailsRepository.save(existingAccount);
        } catch (Exception e) {
            throw new Exception("Error: "  + e.getMessage());
        }
    }
}