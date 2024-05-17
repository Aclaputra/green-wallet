package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Config.Helper;
import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.DataTransferObject.UniversalIDDto;
import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Strings.ApplicationPath;
import fullstuck.green.wallet.security.JWTUtil;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ApplicationPath.MERCHANT)
public class MerchantController {
    private final MerchantService merchantService;
    private final JWTUtil jwtUtil;

    @PostMapping(ApplicationPath.REGISTER)
    public JsonResponse<Object> historyById(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MerchantDTO req) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            Set<ConstraintViolation<MerchantDTO>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<MerchantDTO> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to register")
                        .build();
            }
            return JsonResponse.builder()
                    .statusCode(200)
                    .message("Success !")
                    .data(merchantService.createMerchant(req, userIdFromToken))
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to register merchant")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }
}
