package fullstuck.green.wallet.Model.DataTransferObject;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private int type;
    private String target;
    private String description;
    private BigDecimal amount;
    private BigDecimal curr_balance;
    // Transaction dari User / Merchant kah ?
    // 1 dari akun user
    // 2 dari akun merchant
    private int transFrom;
}