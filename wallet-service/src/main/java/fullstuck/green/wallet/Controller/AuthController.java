package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Config.Helper;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Service.AuthService;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

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
}
