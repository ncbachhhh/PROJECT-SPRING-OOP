package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.INVOICE.InvoiceCreationReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.INVOICE.Invoice;
import DO_AN.OOP.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Tạo hóa đơn mới
    @PostMapping("/create")
    public ApiResponse<Invoice> createInvoice(@RequestBody InvoiceCreationReq req) {
        ApiResponse<Invoice> response = new ApiResponse<>();
        try {
            Invoice invoice = invoiceService.createInvoice(req);
            response.setMessage("Tạo hóa đơn thành công");
            response.setResult(invoice);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Tạo hóa đơn thất bại: " + e.getMessage());
        }
        return response;
    }
}
