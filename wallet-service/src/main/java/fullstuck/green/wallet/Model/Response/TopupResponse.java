package fullstuck.green.wallet.Model.Response;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopupResponse {
    private String amount;
    private String via;
}
