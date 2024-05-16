package fullstuck.green.wallet.Model.Request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TopUpRequestTest {
    @Test
    void testTopUpRequest() {
        TopUpRequest topUpRequest = TopUpRequest.builder()
                .amount(new BigDecimal("100000"))
                .message("success")
                .sourceOfFundId("1")
                .build();
        TopUpRequest topUpRequestGetSet = new TopUpRequest();
        topUpRequestGetSet.setAmount(topUpRequest.getAmount());
        topUpRequestGetSet.setMessage(topUpRequest.getMessage());
        topUpRequestGetSet.setSourceOfFundId(topUpRequest.getSourceOfFundId());

        assertNotNull(topUpRequest);
        assertNotNull(topUpRequestGetSet);
    }
}