package fullstuck.green.wallet.controller;

import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Response.JsonResponse;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Response.TopupResponse;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Model.Response.TransferResponse;
import fullstuck.green.wallet.Service.TransactionService;
import fullstuck.green.wallet.security.JWTUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
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

    @PostMapping("/transfer")
    public JsonResponse<Object> transfer(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        TransferResponse res = transactionService.transfer(req, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .data(res)
                .message("successfully transfer")
                .build();
    }

    @PostMapping("/top-up")
    public JsonResponse<Object> topup(@RequestBody TopUpRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        TopupResponse res = transactionService.topUp(req, userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .message("successfully topup")
                .data(res)
                .build();
    }

    @GetMapping("/get")
    public JsonResponse<Object> history(@RequestHeader("Authorization") String authorizationHeader) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        List<Transaction> data = transactionService.getAllTransaction(userIdFromToken);
        return JsonResponse.builder()
                .statusCode(200)
                .message("GET Data")
                .data(data)
                .build();
    }
}
