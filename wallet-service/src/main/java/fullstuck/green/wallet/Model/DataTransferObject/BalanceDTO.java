package fullstuck.green.wallet.Model.DataTransferObject;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceDTO {
    // Data : Bisa email / nama merchant
    private String data;
    private BigDecimal amount;
    private int transType;
}
