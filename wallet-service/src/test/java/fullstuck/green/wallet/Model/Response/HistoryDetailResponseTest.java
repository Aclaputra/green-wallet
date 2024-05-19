package fullstuck.green.wallet.Model.Response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HistoryDetailResponseTest {
    @Test
    void testHistoryDetailResponse() {
        HistoryDetailResponse response = HistoryDetailResponse.builder()
                .id("id")
                .currBalance(new BigDecimal("2183213"))
                // .userId("id")
                .transType("type")
                .transDate(Date.from(Instant.now()))
                .source("1")
                .amount(new BigDecimal("27821743"))
                .message("success")
                .target("219273982749")
                .build();
        HistoryDetailResponse responseGetSet = new HistoryDetailResponse();
        responseGetSet.setId(response.getId());
        responseGetSet.setAmount(response.getAmount());
        responseGetSet.setSource(response.getSource());
        responseGetSet.setTarget(response.getTarget());
        responseGetSet.setMessage(response.getMessage());
        responseGetSet.setCurrBalance(response.getCurrBalance());
        responseGetSet.setTransType(response.getTransType());
        responseGetSet.setTransDate(response.getTransDate());
        // responseGetSet.setUserId(response.getUserId());

        assertNotNull(response);
        assertNotNull(responseGetSet);
    }

}