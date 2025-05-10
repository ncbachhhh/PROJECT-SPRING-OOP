package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.INVOICE.InvoiceCreationReq;
import DO_AN.OOP.model.ACCOUNT.Account;
import DO_AN.OOP.model.ACCOUNT.Customer;
import DO_AN.OOP.model.INVOICE.Invoice;
import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.repository.ACCOUNT.AccountRepository;
import DO_AN.OOP.repository.INVOICE.InvoiceRepository;
import DO_AN.OOP.repository.ORDER.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Tạo hóa đơn
    public Invoice createInvoice(InvoiceCreationReq req) {
        // 1. Tìm account theo số điện thoại
        Account account = accountRepository.findByPhone(req.getPhone())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với số điện thoại: " + req.getPhone()));

        // 2. Kiểm tra đơn hàng tồn tại
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        Float totalPay = order.getTotalPrice(); // lấy trực tiếp từ đơn hàng

        // 3. Nếu là khách hàng thì tích điểm
        if (account instanceof Customer customer) {
            int earnedPoints = (int) (totalPay / 100000f); // 100k = 1 point
            customer.setPoint(customer.getPoint() + earnedPoints);
            accountRepository.save(customer);
        }

        // 4. Tạo hóa đơn
        Invoice invoice = Invoice.builder()
                .date(LocalDateTime.now())
                .totalPay(totalPay)
                .method(req.getMethod())
                .orderId(req.getOrderId())
                .accId(account.getId())
                .build();

        return invoiceRepository.save(invoice);
    }

}
