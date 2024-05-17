package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Model.DataTransferObject.UniversalIDDto;
import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Response.*;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.TransactionService;
import fullstuck.green.wallet.Strings.ApplicationPath;
import fullstuck.green.wallet.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(ApplicationPath.TRANSACTION)
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountDetailService accountDetailService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public JsonResponse<Object> showTransaction(@RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        List<Transaction> res = transactionService.getAllTransaction(userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(res)
                .message("successfully show transactions")
                .build();
    }

    @PostMapping(ApplicationPath.TRANSFER)
    public JsonResponse<Object> transfer(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        TransferResponse res = transactionService.transfer(req, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(res)
                .message("successfully transfer to other user")
                .build();
    }

    @PostMapping(ApplicationPath.PAYMENT)
    public JsonResponse<Object> payment(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        TransferResponse res = transactionService.payment(req, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(res)
                .message("successfully pay to merchant")
                .build();
    }

    @PostMapping(ApplicationPath.MERCHANT + ApplicationPath.TRANSFER)
    public JsonResponse<Object> merchantTransfer(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        TransferResponse res = transactionService.merchantTransfer(req, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(res)
                .message("success transfer from merchant")
                .build();
    }


    @PostMapping(ApplicationPath.TOPUP)
    public JsonResponse<Object> topup(@RequestBody TopUpRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        TopupResponse res = transactionService.topUp(req, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .message("successfully topup")
                .data(res)
                .build();
    }

    @GetMapping(ApplicationPath.HISTORY + ApplicationPath.ALL)
    public JsonResponse<Object> history(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(ApplicationPath.TRANSACTION+ApplicationPath.HISTORY+ApplicationPath.ALL);
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        List<CustomHistoryInterface> data = transactionService.findAllCustom(userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .message("Found !")
                .data(data)
                .build();
    }

    @GetMapping(ApplicationPath.HISTORY + ApplicationPath.ID)
    public JsonResponse<Object> historyById(@RequestBody UniversalIDDto req) {
        System.out.println("TEST");
        HistoryDetailResponse historyDetailResponse = transactionService.getById(req.getId());
        return JsonResponse.builder()
                .statusCode(200)
                .message("Found !")
                .data(historyDetailResponse)
                .build();
    }

    // Jangan dipake cuma copas get yg di atas
    @GetMapping(ApplicationPath.HISTORY + ApplicationPath.PAGES)
    public JsonResponse<Object> historyPagination(@RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        List<Transaction> data = transactionService.getAllTransaction(userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .message("Found !")
                .data(data)
                .build();
    }
}
