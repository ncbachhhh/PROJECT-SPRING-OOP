package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.INVOICE.InvoiceCreationReq;
import DO_AN.OOP.model.ACCOUNT.Account;
import DO_AN.OOP.model.ACCOUNT.Customer;
import DO_AN.OOP.model.INVOICE.Invoice;
import DO_AN.OOP.model.INVOICE.InvoiceStatus;
import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.repository.ACCOUNT.AccountRepository;
import DO_AN.OOP.repository.INVOICE.InvoiceRepository;
import DO_AN.OOP.repository.ORDER.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .status(InvoiceStatus.CHUA_THANH_TOAN)
                .build();

        return invoiceRepository.save(invoice);
    }

    //    Cập nhật trạng thái
    public Invoice completeInvoice(String invoiceId) {
        System.out.println("Hoàn thành hóa đơn với ID: " + invoiceId);
        // 1. Tìm hóa đơn theo ID
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + invoiceId));
        System.out.println("Hóa đơn tìm thấy: " + invoice);
        System.out.println("Trạng thái hóa đơn: " + invoice.getStatus());
        // 2. Cập nhật trạng thái hóa đơn
        invoice.setStatus(InvoiceStatus.DA_THANH_TOAN);
        System.out.println("Đang cập nhật trạng thái hóa đơn thành DA_THANH_TOAN");
        System.out.println("Trạng thái hóa đơn sau khi cập nhật: " + invoice);
        // 3. Lưu hóa đơn đã cập nhật
        return invoiceRepository.save(invoice);
    }

    //    Lấy tất cả hóa đơn chưa thanh toán
    public List<Invoice> getAllUnpaidInvoices() {
        return invoiceRepository.findByStatus(InvoiceStatus.CHUA_THANH_TOAN);
    }

    //     Xóa hóa đơn
    public void deleteInvoice(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + invoiceId));
        invoiceRepository.delete(invoice);
    }
}
