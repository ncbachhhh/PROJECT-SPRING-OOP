package DO_AN.OOP.dto.request;

import DO_AN.OOP.model.INVOICE.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceCreationReq {
    private Float totalPay;
    private PaymentMethod method;
    private String orderId;
    private String phone; // tra ra accId từ đây
}
