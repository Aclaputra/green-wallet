package fullstuck.green.wallet.Model.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @NotNull(message = "id cannot be empty")
    private String id;
    @Length(min = 4, message = "name length cannot be lesser than 4")
    private String name;
    private String birthDate;
    private String phoneNumber;
    private String profileImageUrl;
}
