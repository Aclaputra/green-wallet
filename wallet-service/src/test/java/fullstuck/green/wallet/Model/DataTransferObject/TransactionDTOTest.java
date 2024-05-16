package fullstuck.green.wallet.Model.DataTransferObject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDTOTest {
    @Test
    void testTransactionDTO() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .type(1)
                .description("description")
                .curr_balance(new BigDecimal("200000"))
                .amount(new BigDecimal("100000"))
                .transFrom(1)
                .build();
        TransactionDTO transactionDTOGetSet = new TransactionDTO();
        transactionDTOGetSet.setAmount(transactionDTOGetSet.getAmount());
        transactionDTOGetSet.setType(transactionDTO.getType());
        transactionDTOGetSet.setTarget(transactionDTO.getTarget());
        transactionDTOGetSet.setDescription(transactionDTO.getDescription());
        transactionDTOGetSet.setTransFrom(transactionDTO.getTransFrom());
        transactionDTOGetSet.setCurr_balance(transactionDTO.getCurr_balance());

        assertNotNull(transactionDTO);
        assertNotNull(transactionDTOGetSet);
    }

}