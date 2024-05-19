package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Config.Patcher;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.UpdateProfileRequest;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.UserService;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AccountDetailsRepository accountDetailsRepository;

    @Test
    public void testGetProfileDetail() {
        AccountDetails accountDetails = AccountDetails.builder()
                .user(User.builder().build())
                .role(Role.builder().build())
                .build();
        when(accountDetailsRepository.findById("123")).thenReturn(Optional.ofNullable(accountDetails));
        ProfileDetailResponse profileDetailResponse = userService.getProfileDetail("123");
        System.out.println(profileDetailResponse);
        assertNotNull(profileDetailResponse);
    }

    @Test
    public void getUserUpdateMerchant() {
        User user = User.builder().build();
        userService.userUpdateMerchant(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetByPhone() {
        User user = User.builder().build();
        when(userRepository.findByphone("08162816382")).thenReturn(user);
        User userFound = userService.getByPhone("08162816382");
        assertEquals(user, userFound);
    }

    @Test
    public void testGetUserById() {
        User user = User.builder().build();
        when(userRepository.findById("123")).thenReturn(Optional.ofNullable(user));
        User userFound = userService.getUserById("123");
        assertEquals(user, userFound);
    }

    @Test
    public void testLoadByUsername() {
        AccountDetails accountDetails = AccountDetails.builder()
                .user(User.builder().build())
                .role(Role.builder().build())
                .build();
        when(accountDetailsRepository.findByemail("acla@gmail.com")).thenReturn(accountDetails);
        var userDetailFound = userService.loadUserByUsername("acla@gmail.com");
        assertNotNull(userDetailFound);
    }

    @Test
    public void testLoadByUserNameNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("hacker@gmail.com");
        });
    }

    @Test
    void testUpdateProfileDetail() throws Exception {
        AccountDetails accountDetails = mock(AccountDetails.class, RETURNS_MOCKS);
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .id("id")
                .phoneNumber("8263986215")
                .profileImageUrl("url")
                .name("name")
                .birthDate("12-11-2000")
                .build();
        String token = "token";
        when(accountDetailsRepository.findById(anyString())).thenReturn(Optional.ofNullable(accountDetails));
        userService.updateProfileDetail(updateProfileRequest, token);
        verify(accountDetailsRepository, times(1)).findAll();
    }


}