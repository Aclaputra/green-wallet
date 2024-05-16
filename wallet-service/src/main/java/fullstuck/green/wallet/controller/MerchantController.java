package fullstuck.green.wallet.controller;

import fullstuck.green.wallet.Model.DataTransferObject.MerchantDTO;
import fullstuck.green.wallet.Model.DataTransferObject.UniversalIDDto;
import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Service.MerchantService;
import fullstuck.green.wallet.Strings.ApplicationPath;
import fullstuck.green.wallet.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ApplicationPath.MERCHANT)
public class MerchantController {
    private final MerchantService merchantService;
    private final JWTUtil jwtUtil;

    @PostMapping(ApplicationPath.REGISTER)
    public JsonResponse<Object> historyById(@RequestHeader("Authorization") String authorizationHeader, @RequestBody MerchantDTO req) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        return JsonResponse.builder()
                .statusCode(200)
                .message("Success !")
                .data(merchantService.createMerchant(req, userIdFromToken))
                .build();
    }
}
