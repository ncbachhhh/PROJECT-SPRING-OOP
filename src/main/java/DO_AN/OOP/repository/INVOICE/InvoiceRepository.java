package DO_AN.OOP.repository.INVOICE;

import DO_AN.OOP.model.INVOICE.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
