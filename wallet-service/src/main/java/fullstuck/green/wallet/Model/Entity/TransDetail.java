package fullstuck.green.wallet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "transaction_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String type;
    private String target_id;
    private String source_id;
    private String description;
    private BigDecimal amount;
    private BigDecimal curr_balance;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
