package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Config.Helper;
import fullstuck.green.wallet.Model.DataTransferObject.UniversalIDDto;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.PasswordResetToken;
import fullstuck.green.wallet.Model.Entity.User;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Request.ResetRequest;
import fullstuck.green.wallet.Model.Response.*;
import fullstuck.green.wallet.Repository.PasswordResetRepository;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.Implementator.MailingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AccountDetailService accountDetailService;
    private final MailingService mailingService;
    private final PasswordResetRepository passwordResetRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public JsonResponse<Object> register(@RequestBody RegisterRequest request) {
        try {
            Set<ConstraintViolation<RegisterRequest>> violations = Helper.validator.validate(request);
            if (!violations.isEmpty()) {
                ConstraintViolation<RegisterRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to register")
                        .build();
            }
            RegisterResponse response = authService.register(request);
            return JsonResponse.builder()
                    .statusCode(201)
                    .data(response)
                    .message("successfully registered")
                    .build();
        } catch (Exception | Error e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to register")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PostMapping("/login")
    public JsonResponse<Object> login(@RequestBody LoginRequest request) {
        try {
            Set<ConstraintViolation<LoginRequest>> violations = Helper.validator.validate(request);
            if (!violations.isEmpty()) {
                ConstraintViolation<LoginRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to login")
                        .build();
            }
            LoginResponse response = authService.login(request);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(response)
                    .message("successfully login")
                    .build();
        } catch (Exception | Error e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to login")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PostMapping("/reset")
    public ForgotPasswordResponse resetPassword(HttpServletResponse response, @RequestBody UniversalIDDto req) throws Exception {
        AccountDetails accountDetails = accountDetailService.getByEmail(req.getId());
        PasswordResetToken passwordResetToken = passwordResetRepository.findByemail(req.getId());

        if(accountDetails != null){
            if(passwordResetToken != null){
                if(passwordResetToken.getExpired_at().after(Date.from(Instant.now()))){
                    passwordResetRepository.delete(passwordResetToken);
                    return reset(accountDetails, req.getId());
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return ForgotPasswordResponse.builder()
                            .email(req.getId())
                            .message("Previous request still active !")
                            .build();
                }
            } else {
                return reset(accountDetails, req.getId());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ForgotPasswordResponse.builder()
                    .email(req.getId())
                    .message("Username not found")
                    .build();
        }
    }

    public ForgotPasswordResponse reset(AccountDetails accountDetails, String email){
        String token = UUID.randomUUID().toString();
        String url = "http://localhost:4200/greenpay/forgot-password?token=" + token;
        ForgotPasswordResponse forgotPasswordResponse = null;
        try {
            forgotPasswordResponse = authService.resetPassword(accountDetails, token);
            mailingService.simpleResetPassword(accountDetails.getEmail(), url);
            return forgotPasswordResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/reset-password")
    public JsonResponse<Object> changePasswordPage(HttpServletResponse res, @RequestBody ResetRequest req){
        ResetResponse response = authService.validateResetToken(req.getToken());
        if(response.getStatus()){
            AccountDetails accountDetails = accountDetailService.getByEmail(req.getEmail());
            accountDetails.setPassword(passwordEncoder.encode(req.getPassword()));
            accountDetailService.updateAccountData(accountDetails);
            authService.deleteOldToken(req.getToken());

            return JsonResponse.builder()
                    .statusCode(200)
                    .data(response)
                    .message("Success updating data !")
                    .build();
        } else {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return JsonResponse.builder()
                    .statusCode(403)
                    .data(response)
                    .message("Token expired / request not exist !")
                    .build();
        }
    }
}
