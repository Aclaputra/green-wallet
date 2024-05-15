package fullstuck.green.wallet;

import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.Implementator.UserServiceImpl;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.Strings.RoleEnum;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTests {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountDetailService accountDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private User userResponse = new User();
    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setName("John Doe");
        registerRequest.setBirthDate("1990-01-01 00:00:00");
        registerRequest.setPhoneNumber("1234567890");
        registerRequest.setUsername("johndoe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password");
        userResponse.setName(registerRequest.getName());
        userResponse.setPhone(registerRequest.getPhoneNumber());
    }

    @Test
    public void testRegisterFunction() {
        RegisterResponse res = authService.register(registerRequest);
        assertNotNull(res);
    }

    @Test
    public void testLoginFunction() {
        LoginResponse res = authService.login(LoginRequest.builder()
                .email("johndoe@example.com")
                .password("password")
                .build());
        assertNotNull(res);
    }
}
