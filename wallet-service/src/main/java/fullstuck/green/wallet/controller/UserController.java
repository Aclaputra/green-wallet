package fullstuck.green.wallet.controller;

import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Model.Response.LoginResponse;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;
    private final MerchantService merchantService;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
    public JsonResponse<Object> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return JsonResponse.builder()
                .statusCode(201)
                .data(response)
                .message("successfully registered")
                .build();
    }

//    @PostMapping("/login")
//    public JsonResponse<Object> login(@RequestBody LoginRequest request) {
//        LoginResponse response = authService.login(request);
//    }

    @GetMapping("/profile")
    public JsonResponse<Object> profileDetail(@RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        ProfileDetailResponse res = userService.getProfileDetail(userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(res)
                .message("successfully get profile detail")
                .build();
    }

    @PostMapping("/create-merchant")
    public JsonResponse<Object> newMerchant(@RequestBody MerchantDTO merchantDTO, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        merchantService.createMerchant(merchantDTO, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(merchantDTO)
                .message("success create new merchant !")
                .build();
    }
}
