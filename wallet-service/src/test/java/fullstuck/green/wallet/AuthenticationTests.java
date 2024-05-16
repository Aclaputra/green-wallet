package fullstuck.green.wallet;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.AppUser;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Repository.AccountDetailsRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.Implementator.UserServiceImpl;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.Strings.RoleEnum;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthenticationTests {

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



//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private RegisterRequest registerRequest;
//    private User userResponse = new User();
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private AccountDetailService accountDetailService;
//
//    private String userId;
//
//    @BeforeEach
//    public void setUp() {
//        registerRequest = new RegisterRequest();
//        registerRequest.setName("John Doe");
//        registerRequest.setBirthDate("1990-01-01 00:00:00");
//        registerRequest.setPhoneNumber("1234567890");
//        registerRequest.setUsername("johndoe");
//        registerRequest.setEmail("johndoe@example.com");
//        registerRequest.setPassword("password");
//        userResponse.setName(registerRequest.getName());
//        userResponse.setPhone(registerRequest.getPhoneNumber());
//    }
//
//    @Test
//    public void testAuthentication() {
//        RegisterResponse registerRes = authService
//                .register(registerRequest);
//        System.out.println("Rgist: " + registerRes);
//        userId = registerRes.getId();
//        assertNotNull(registerRes);
//
//        LoginResponse loginRes;
//        loginRes = authService.login(LoginRequest.builder()
//                .email("johndoe@example.com")
//                .password("password")
//                .build());
//        assertNotNull(loginRes);
//    }
//
//    @Test
//    public void testUserNotFoundExceptionWhileLogin() {
//        assertThrows(Error.class, () -> {
//            authService.login(LoginRequest.builder()
//                    .email("notfound@example.com")
//                    .password("password")
//                    .build());
//        });
//    }
//
//    @Test
//    public void testGetProfileDetail() {
//
//    }

}
