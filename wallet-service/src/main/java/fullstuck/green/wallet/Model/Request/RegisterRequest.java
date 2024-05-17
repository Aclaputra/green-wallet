package fullstuck.green.wallet.Model.Request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    @NotNull
    private String username;
    @NotNull
    @Email(message = "email should be valid")
    private String email;
    @NotNull
    @Length(min = 8, message = "password length must be greater than equal 8")
    @Length(max = 127, message = "password length cannot be greater than equal 127")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;
    @NotNull
    private String phoneNumber;
    private String birthDate;
}
