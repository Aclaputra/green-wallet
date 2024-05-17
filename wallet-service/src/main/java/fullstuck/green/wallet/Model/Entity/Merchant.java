package fullstuck.green.wallet.Model.Entity;

import fullstuck.green.wallet.Strings.MerchantEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLSelect;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "master_merchant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@SQLSelect(sql = "SELECT * FROM master_merchant WHERE isDeleted = false")
@SQLDelete(sql = "UPDATE master_merchant SET isDeleted = true WHERE id = ?")
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
