package fullstuck.green.wallet.Model.Request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String destination;
    private BigDecimal amount;
    private String message;
}
