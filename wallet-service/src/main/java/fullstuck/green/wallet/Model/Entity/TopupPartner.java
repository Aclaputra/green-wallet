package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "master_partner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TopupPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    private BigDecimal total;
    private Boolean isDeleted;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}