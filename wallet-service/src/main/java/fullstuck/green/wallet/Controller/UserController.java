package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Config.Helper;
import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.Request.UpdateProfileRequest;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Model.Response.ProfileDetailResponse;
import fullstuck.green.wallet.Service.UserService;
import fullstuck.green.wallet.security.JWTUtil;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;
    private final MerchantService merchantService;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/profile")
    public JsonResponse<Object> profileDetail(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            ProfileDetailResponse res = userService.getProfileDetail(userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(res)
                    .message("successfully get profile detail")
                    .build();
        } catch (NoSuchElementException e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .message("user not found")
                    .data(e.getMessage())
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .message("failed to update")
                    .data(e.getMessage())
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PatchMapping("/profile")
    public JsonResponse<Object> updateProfileDetail(@RequestBody UpdateProfileRequest incompleteUpdateProfile, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            Set<ConstraintViolation<UpdateProfileRequest>> violations = Helper.validator.validate(incompleteUpdateProfile);
            if (!violations.isEmpty()) {
                ConstraintViolation<UpdateProfileRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to update profile")
                        .build();
            }
            userService.updateProfileDetail(incompleteUpdateProfile, userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(201)
                    .message("successfully updated")
                    .data(userIdFromToken)
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .message("failed to update profile")
                    .data(e.getMessage())
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PostMapping("/create-merchant")
    public JsonResponse<Object> newMerchant(@RequestBody MerchantDTO merchantDTO, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            Set<ConstraintViolation<MerchantDTO>> violations = Helper.validator.validate(merchantDTO);
            if (!violations.isEmpty()) {
                ConstraintViolation<MerchantDTO> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to create merchant")
                        .build();
            }
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            merchantService.createMerchant(merchantDTO, userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(merchantDTO)
                    .message("success create new merchant !")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .message("failed to create merchant")
                    .data(e.getMessage())
                    .build();
        } finally {
            Helper.factory.close();
        }
    }
}
