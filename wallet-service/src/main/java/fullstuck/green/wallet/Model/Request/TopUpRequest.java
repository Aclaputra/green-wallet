package fullstuck.green.wallet.Model.Request;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest {
    private String amount;
    private String sourceOfFundId;
}
