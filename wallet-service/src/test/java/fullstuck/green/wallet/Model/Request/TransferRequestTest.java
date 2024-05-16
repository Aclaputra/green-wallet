package fullstuck.green.wallet.Model.Request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferRequestTest {
    @Test
    void testTransferReqeuest() {
        TransferRequest transferRequest = TransferRequest.builder()
                .destination("2971092732")
                .amount(new BigDecimal("10000"))
                .message("success")
                .build();
        TransferRequest transferRequestGetSet = new TransferRequest();
        transferRequestGetSet.setAmount(transferRequest.getAmount());
        transferRequestGetSet.setMessage(transferRequest.getMessage());
        transferRequestGetSet.setDestination(transferRequest.getDestination());

        assertNotNull(transferRequest);
        assertNotNull(transferRequestGetSet);
    }
}