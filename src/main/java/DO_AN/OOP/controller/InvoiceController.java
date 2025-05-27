package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.INVOICE.InvoiceCreationReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.INVOICE.Invoice;
import DO_AN.OOP.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/complete/{invoiceId}")
    public ApiResponse<Invoice> completeInvoice(@PathVariable String invoiceId) {
        ApiResponse<Invoice> response = new ApiResponse<>();
        try {
            Invoice invoice = invoiceService.completeInvoice(invoiceId);
            response.setMessage("Hoàn thành hóa đơn thành công");
            response.setResult(invoice);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Hoàn thành hóa đơn thất bại: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/get/unpaid")
    public ApiResponse<List<Invoice>> getUnpaidInvoice() {
        ApiResponse<List<Invoice>> response = new ApiResponse<>();
        try {
            List<Invoice> invoices = invoiceService.getAllUnpaidInvoices();
            response.setMessage("Lấy danh sách hóa đơn chưa thanh toán thành công");
            response.setResult(invoices);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy danh sách hóa đơn chưa thanh toán thất bại: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/delete/{invoiceId}")
    public ApiResponse<String> deleteInvoice(@PathVariable String invoiceId) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            invoiceService.deleteInvoice(invoiceId);
            response.setMessage("Xóa hóa đơn thành công");
            response.setResult("Hóa đơn đã được xóa");
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Xóa hóa đơn thất bại: " + e.getMessage());
        }
        return response;
    }
}
