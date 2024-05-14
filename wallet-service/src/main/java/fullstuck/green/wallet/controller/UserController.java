package fullstuck.green.wallet.controller;

import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final AuthService authService;

    @PostMapping("/register")
    public JsonResponse<Object> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        System.out.println("response: " + response);
        return JsonResponse.builder()
                .statusCode(201)
                .data(response)
                .message("successfully registered")
                .build();
    }

    @PostMapping("/login")
    public JsonResponse<Object> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        System.out.println("response: " + response);
        return JsonResponse.builder()
                .statusCode(200)
                .data(response)
                .message("successfully login")
                .build();
    }
}
