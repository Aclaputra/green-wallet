package fullstuck.green.wallet;

import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthorizationTests {
    @Autowired
    private UserService userService;
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



}
