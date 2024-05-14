package fullstuck.green.wallet.Model.Request;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private String destination;
    private String amount;
    private String description;
}
