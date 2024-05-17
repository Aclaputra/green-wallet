package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Config.Helper;
import fullstuck.green.wallet.Model.DataTransferObject.NewPartnerDTO;
import fullstuck.green.wallet.Model.Request.LoginRequest;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Service.TopupPartnerService;
import fullstuck.green.wallet.Strings.ApplicationPath;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ApplicationPath.PARTNER)
public class PartnerController {
    private final TopupPartnerService topupPartnerService;

    @PostMapping(ApplicationPath.REGISTER)
    public JsonResponse<Object> registerPartner(@RequestBody NewPartnerDTO req) {
        try {
            Set<ConstraintViolation<NewPartnerDTO>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<NewPartnerDTO> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to register partner")
                        .build();
            }
            topupPartnerService.registerNewPartner(req);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(topupPartnerService.getPartnerByName(req.getName()))
                    .message("Success create new Partner !")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to register partner")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @GetMapping
    public JsonResponse<Object> getPartnerData(@RequestBody NewPartnerDTO req) {
        try {
            Set<ConstraintViolation<NewPartnerDTO>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<NewPartnerDTO> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to get partner data")
                        .build();
            }
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(topupPartnerService.getPartnerByName(req.getName()))
                    .message("Found !")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to get partner data")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @GetMapping(ApplicationPath.ID)
    public JsonResponse<Object> getById(@RequestBody NewPartnerDTO req) {
        try {
            Set<ConstraintViolation<NewPartnerDTO>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<NewPartnerDTO> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to get partner by id")
                        .build();
            }
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(topupPartnerService.getPartnerData(req.getId()))
                    .message("Found !")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to get partner by id")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PutMapping(ApplicationPath.UPDATE)
    public JsonResponse<Object> updatePartnerName(@RequestBody NewPartnerDTO req) {
        try {
            Set<ConstraintViolation<NewPartnerDTO>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<NewPartnerDTO> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to update partner name")
                        .build();
            }
            if(!topupPartnerService.updatePartner(req)){
                return JsonResponse.builder()
                        .statusCode(200)
                        .data(req)
                        .message("Success create new Partner !")
                        .build();
            }
            else {
                return JsonResponse.builder()
                        .statusCode(404)
                        .data(req)
                        .message("Partner not found !")
                        .build();
            }
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to update partner name")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }
}
