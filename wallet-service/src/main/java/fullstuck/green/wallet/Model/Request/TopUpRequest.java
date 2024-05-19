package fullstuck.green.wallet.Model.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest {
    @NotNull
//   @DecimalMin(value = "1", inclusive = false)
    private BigDecimal amount;
    @NotNull
    private String sourceOfFundId;
    private String message;
}
