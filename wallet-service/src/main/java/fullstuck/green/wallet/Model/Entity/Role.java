package fullstuck.green.wallet.Model.Entity;

import fullstuck.green.wallet.Strings.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "master_role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private RoleEnum name;
}
