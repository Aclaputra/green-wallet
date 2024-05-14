package fullstuck.green.wallet.Model.Response;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String jwtToken;
}
