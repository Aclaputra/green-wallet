package fullstuck.green.wallet.Model.Request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest {
    private BigDecimal amount;
    private String sourceOfFundId;
    private String message;
}
