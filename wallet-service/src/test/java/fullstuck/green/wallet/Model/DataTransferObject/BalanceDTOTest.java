package fullstuck.green.wallet.Model.DataTransferObject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BalanceDTOTest {
    @Test
    void testBalanceDTO() {
        BalanceDTO balanceDTO = BalanceDTO.builder()
                .amount(new BigDecimal("100000"))
                .data("data")
                .transType(1)
                .build();
        BalanceDTO balanceDTOGetSet = new BalanceDTO();
        balanceDTOGetSet.setAmount(balanceDTO.getAmount());
        balanceDTOGetSet.setData(balanceDTO.getData());
        balanceDTOGetSet.setTransType(balanceDTO.getTransType());

        assertNotNull(balanceDTO);
        assertNotNull(balanceDTOGetSet);
    }
}