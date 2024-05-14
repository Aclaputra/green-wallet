package fullstuck.green.wallet.Model.Entity;

import fullstuck.green.wallet.Strings.MerchantEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
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
    private BigDecimal balance = new BigDecimal("0.0");
    private Date created_at = Date.from(Instant.now());
    private Date updated_at = Date.from(Instant.now());
    private Date deleted_at;
    private Boolean isDeleted = Boolean.FALSE;
}
