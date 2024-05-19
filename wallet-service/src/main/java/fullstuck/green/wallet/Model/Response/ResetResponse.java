package fullstuck.green.wallet.Model.Response;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetResponse {
    private String email;
    private String message;
    private Boolean status;
}