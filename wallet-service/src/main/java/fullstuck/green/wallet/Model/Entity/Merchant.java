package fullstuck.green.wallet.Model.Entity;

import fullstuck.green.wallet.Strings.MerchantEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "master_merchant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    private MerchantEnum type;
    private Boolean isGreen;
    private BigDecimal balance;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private Boolean isDeleted;
}
