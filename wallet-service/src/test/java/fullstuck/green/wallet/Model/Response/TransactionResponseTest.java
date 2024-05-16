package fullstuck.green.wallet.Model.Response;

import fullstuck.green.wallet.Model.Entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionResponseTest {
    @Test
    void testTransactionResponse() {
        TransactionResponse res = TransactionResponse.builder()
                .id("123")
                .via("via")
                .type("type")
                .destination("9832713213")
                .from("12873823")
                .description("desc")
                .build();
        TransactionResponse resGetSet = new TransactionResponse();
        resGetSet.setId(res.getId());
        resGetSet.setFrom(res.getFrom());
        resGetSet.setType(res.getType());
        resGetSet.setAmount(res.getAmount());
        resGetSet.setDestination(res.getDestination());
        resGetSet.setDescription(res.getDescription());
        resGetSet.setVia(res.getVia());

        assertEquals(res, resGetSet);
    }
}