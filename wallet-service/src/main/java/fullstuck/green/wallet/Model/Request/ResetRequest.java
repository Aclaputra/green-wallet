package fullstuck.green.wallet.Model.Request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetRequest {
    private String email;
    private String password;
}
