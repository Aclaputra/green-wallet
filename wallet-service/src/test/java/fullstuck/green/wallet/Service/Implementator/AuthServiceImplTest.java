package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.Merchant;
import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Repository.MerchantRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Strings.RoleEnum;
import fullstuck.green.wallet.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MerchantRepository merchantRepository;
    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AccountDetailService accountDetailService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .email("acla@gmail.com")
                .name("acla")
                .birthDate("12-11-2000")
                .phoneNumber("082738216")
                .username("aclaputra")
                .password("password")
                .build();
        loginRequest = LoginRequest.builder()
                .email("acla@gmail.com")
                .password("password")
                .build();
        user = User.builder()
                .id("123")
                .name("Muhammad Acla")
                .birthDate(Date.from(Instant.now()))
                .phone("0821387213")
                .isDeleted(false)
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .merchant(Merchant.builder().build())
                .build();
    }

    @Test
    void testRegister() {
        User mockUser = mock(User.class, RETURNS_MOCKS);
        Role mockRole = mock(Role.class, RETURNS_MOCKS);
        when(roleService.getOrSave(RoleEnum.ROLE_USER)).thenReturn(mockRole);
        when(userRepository.save(any())).thenReturn(mockUser);
        RegisterResponse response = authService.register(registerRequest);
        verify(accountDetailService, times(1)).createAccount(any());
    }

    @Test
    void testLogin() {
        Authentication authentication = mock(Authentication.class, RETURNS_DEFAULTS);
        String token = "token";
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(any())).thenReturn(token);
        LoginResponse response = authService.login(loginRequest);
        assertNotNull(response);
    }
}