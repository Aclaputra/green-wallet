package fullstuck.green.wallet.Model.Entity;

import fullstuck.green.wallet.Strings.TransactionType;
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
    private TransactionType type;
    private String target_id;
    private String source_id;
    private String description;
    private BigDecimal amount;
    private BigDecimal curr_balance;
    private Date created_at = Date.from(Instant.now());
    private Date updated_at = Date.from(Instant.now());
    private Date deleted_at;
}
