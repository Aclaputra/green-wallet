package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Repository.MerchantRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Strings.RoleEnum;
import fullstuck.green.wallet.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountDetailService accountDetailService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest req) throws ResponseStatusException {
        try {
            Role role = roleService.getOrSave(RoleEnum.ROLE_USER);

            Date birthDate = DateUtils.parseDate(req.getBirthDate(),
                    new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
            User user = User.builder()
                    .merchant(null)
                    .name(req.getName())
                    .birthDate(birthDate)
                    .phone(req.getPhoneNumber())
                    .created_at(Date.from(Instant.now()))
                    .updated_at(Date.from(Instant.now()))
                    .isDeleted(Boolean.FALSE)
                    .build();
            User savedUser = userRepository.save(user);

            AccountDetails accountDetails = AccountDetails.builder()
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .balance(new BigDecimal("0"))
                    .point(new BigDecimal("0"))
                    .role(role)
                    .user(user)
                    .balance(new BigDecimal("0.0"))
                    .profileImageUrl("https://i.pinimg.com/474x/65/25/a0/6525a08f1df98a2e3a545fe2ace4be47.jpg")
                    .created_at(Date.from(Instant.now()))
                    .updated_at(Date.from(Instant.now()))
                    .isDeleted(Boolean.FALSE)
                    .isVerified(Boolean.FALSE)
                    .build();
            accountDetailService.createAccount(accountDetails);

            return RegisterResponse.builder()
                    .id(savedUser.getId())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.getEmail(),
                    req.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            AppUser appUser = (AppUser) authentication.getPrincipal();

            String token = jwtUtil.generateToken(appUser);
            return LoginResponse.builder()
                    .jwtToken(token)
                    .build();
        } catch (Exception e) {
            throw new Error("Error: " + e.getMessage());
        }
    }
}
