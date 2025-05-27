package DO_AN.OOP.repository.INVOICE;

import DO_AN.OOP.model.INVOICE.Invoice;
import DO_AN.OOP.model.INVOICE.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByStatus(InvoiceStatus status);
}
