package DO_AN.OOP.model.INVOICE;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime date;

    private Float totalPay;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String orderId;

    private String accId; // mã khách hàng
}
