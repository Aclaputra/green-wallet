package fullstuck.green.wallet.Model.Response;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordResponse {
    private String email;
    private String message;
}