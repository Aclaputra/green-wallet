package fullstuck.green.wallet.Controller;

import fullstuck.green.wallet.Config.Helper;
import fullstuck.green.wallet.Model.DataTransferObject.UniversalIDDto;
import fullstuck.green.wallet.Model.Entity.AccountDetails;
import fullstuck.green.wallet.Model.Entity.Transaction;
import fullstuck.green.wallet.Model.Request.TopUpRequest;
import fullstuck.green.wallet.Model.Response.*;
import fullstuck.green.wallet.Model.Request.TransferRequest;
import fullstuck.green.wallet.Service.AccountDetailService;
import fullstuck.green.wallet.Service.TransactionService;
import fullstuck.green.wallet.Strings.ApplicationPath;
import fullstuck.green.wallet.security.JWTUtil;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Set;
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
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            List<Transaction> res = transactionService.getAllTransaction(userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(res)
                    .message("successfully show transactions")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to show transactions")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PostMapping(ApplicationPath.TRANSFER)
    public JsonResponse<Object> transfer(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            Set<ConstraintViolation<TransferRequest>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<TransferRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to transfer")
                        .build();
            }
            TransferResponse res = transactionService.transfer(req, userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(res)
                    .message("successfully transfer to other user")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to transfer")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PostMapping(ApplicationPath.PAYMENT)
    public JsonResponse<Object> payment(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            Set<ConstraintViolation<TransferRequest>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<TransferRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to register")
                        .build();
            }
            TransferResponse res = transactionService.payment(req, userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(res)
                    .message("successfully pay to merchant")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to do payment")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @PostMapping(ApplicationPath.MERCHANT + ApplicationPath.TRANSFER)
    public JsonResponse<Object> merchantTransfer(@RequestBody TransferRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            Set<ConstraintViolation<TransferRequest>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<TransferRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to merchant transfer")
                        .build();
            }
            TransferResponse res = transactionService.merchantTransfer(req, userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .data(res)
                    .message("success transfer from merchant")
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to do merchant transfer")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }


    @PostMapping(ApplicationPath.TOPUP)
    public JsonResponse<Object> topup(@RequestBody TopUpRequest req, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            Set<ConstraintViolation<TopUpRequest>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<TopUpRequest> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to topup")
                        .build();
            }
            TopupResponse res = transactionService.topUp(req, userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .message("successfully topup")
                    .data(res)
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to topup")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    @GetMapping(ApplicationPath.HISTORY + ApplicationPath.ALL)
    public JsonResponse<Object> history(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
            List<CustomHistoryInterface> data = transactionService.findAllCustom(userIdFromToken);
            return JsonResponse.builder()
                    .statusCode(200)
                    .message("Found !")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to get all history")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }


    //Change this from get to post
    @PostMapping(ApplicationPath.HISTORY + ApplicationPath.ID)
    public JsonResponse<Object> historyById(@RequestBody UniversalIDDto req) {
        try {
            Set<ConstraintViolation<UniversalIDDto>> violations = Helper.validator.validate(req);
            if (!violations.isEmpty()) {
                ConstraintViolation<UniversalIDDto> violationMessage = violations.stream().findFirst().get();
                return JsonResponse.builder()
                        .statusCode(500)
                        .data(violationMessage.getMessage())
                        .message("failed to get history by id")
                        .build();
            }
            System.out.println("TEST");
            HistoryDetailResponse historyDetailResponse = transactionService.getByIdSpecial(req.getId());
            return JsonResponse.builder()
                    .statusCode(200)
                    .message("Found !")
                    .data(historyDetailResponse)
                    .build();
        } catch (Exception e) {
            return JsonResponse.builder()
                    .statusCode(500)
                    .data(e.getMessage())
                    .message("failed to get history by id")
                    .build();
        } finally {
            Helper.factory.close();
        }
    }

    // Pagination uda work
    @GetMapping(ApplicationPath.HISTORY + ApplicationPath.PAGES)
    public PageResponseWrapper<CustomHistoryInterface> historyPagination(@RequestHeader("Authorization") String authorizationHeader,
                                                                         @RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                                                                         @RequestParam(name = "size", defaultValue = "20") Integer pageSize,
                                                                         @RequestParam(name = "sortBy", defaultValue = "DESC") String sortDir) {
        String userIdFromToken = jwtUtil.getUserInfoByToken(authorizationHeader.substring(7)).get("userId");
        List<Transaction> data = transactionService.getAllTransaction(userIdFromToken);
        System.out.println("Masuk pak eko");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDir), "transDate"));
        Page<CustomHistoryInterface> page = transactionService.getHistoryPerPage(pageable, userIdFromToken);
        return new PageResponseWrapper<>(page);
    }

    @GetMapping(ApplicationPath.ALL + ApplicationPath.COUNT)
    public JsonResponse<Object> getDailyTrans(@RequestHeader("Authorization") String token){
        String id = jwtUtil.getUserInfoByToken(token.substring(7)).get("userId");
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(id);
        return JsonResponse.builder()
                .statusCode(200)
                .data(transactionService.getTransCount(accountDetails.getUser().getId()))
                .message("Result")
                .build();
    }

    @GetMapping(ApplicationPath.ALL + ApplicationPath.SUM)
    public JsonResponse<Object> getDailySum(@RequestHeader("Authorization") String token){
        String id = jwtUtil.getUserInfoByToken(token.substring(7)).get("userId");
        AccountDetails accountDetails = accountDetailService.getAccountDetailById(id);
        return JsonResponse.builder()
                .statusCode(200)
                .data(transactionService.getDailySum(accountDetails.getUser().getId()))
                .message("Result")
                .build();
    }
}
