package fullstuck.green.wallet.controller;

import fullstuck.green.wallet.Model.DataTransferObject.NewPartnerDTO;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Service.TopupPartnerService;
import fullstuck.green.wallet.Strings.ApplicationPath;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ApplicationPath.PARTNER)
public class PartnerController {
    private final TopupPartnerService topupPartnerService;

    // /partner/register
    @PostMapping(ApplicationPath.REGISTER)
    public JsonResponse<Object> registerPartner(@RequestBody NewPartnerDTO req) {
        topupPartnerService.registerNewPartner(req);
        return JsonResponse.builder()
                .statusCode(200)
                .data(topupPartnerService.getPartnerByName(req.getName()))
                .message("Success create new Partner !")
                .build();
    }

    @GetMapping
    public JsonResponse<Object> getPartnerData(@RequestBody NewPartnerDTO req) {
        return JsonResponse.builder()
                .statusCode(200)
                .data(topupPartnerService.getPartnerByName(req.getName()))
                .message("Found !")
                .build();
    }

    @GetMapping(ApplicationPath.ID)
    public JsonResponse<Object> getById(@RequestBody NewPartnerDTO req) {
        return JsonResponse.builder()
                .statusCode(200)
                .data(topupPartnerService.getPartnerData(req.getId()))
                .message("Found !")
                .build();
    }

    @PutMapping(ApplicationPath.UPDATE)
    public JsonResponse<Object> updatePartnerName(@RequestBody NewPartnerDTO req) {
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
    }
}
