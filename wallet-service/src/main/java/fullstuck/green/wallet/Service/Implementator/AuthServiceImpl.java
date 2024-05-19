package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.ResetResponse;
import fullstuck.green.wallet.Model.Response.ForgotPasswordResponse;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Repository.PasswordResetRepository;
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
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountDetailService accountDetailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetRepository passwordResetRepository;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest req) throws Exception {
        try {
            Role role = roleService.getOrSave(RoleEnum.ROLE_USER);

            Date birthDate = DateUtils.parseDate(req.getBirthDate(),
                    new String[] { "dd-MM-yyyy" });
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
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public LoginResponse login(LoginRequest req) throws Exception{
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

    @Override
    public ForgotPasswordResponse resetPassword(AccountDetails accountDetails, String token) {
        Date date = Date.from(Instant.now());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        passwordResetRepository.save(PasswordResetToken.builder()
                .accountDetails(accountDetails)
                .email(accountDetails.getEmail())
                .token(token)
                .expired_at(calendar.getTime())
                .build());
        return ForgotPasswordResponse.builder()
                .message("Generate token success !")
                .email(accountDetails.getEmail())
                .build();
    }

    @Override
    public ResetResponse validateResetToken(String token) {
        PasswordResetToken reset = passwordResetRepository.findBytoken(token);
        System.out.println(reset.toString());
        String email = reset.getEmail();
        if(reset == null){
            throw new RuntimeException("Request Invalid !");
        } else {
            if(reset.getExpired_at().before(Date.from(Instant.now()))){
                ResetResponse response = ResetResponse.builder()
                        .email(email)
                        .message("Request failed / token expired !")
                        .status(Boolean.FALSE)
                        .build();
                System.out.println(response);
                return response;
            } else {
                ResetResponse response = ResetResponse.builder()
                        .email(email)
                        .message("Token validated !")
                        .status(Boolean.TRUE)
                        .build();
                System.out.println(response);
                return response;
            }
        }
    }

    @Override
    public void deleteOldToken(String token) {
        PasswordResetToken reset = passwordResetRepository.findBytoken(token);
        passwordResetRepository.delete(reset);
    }
}
