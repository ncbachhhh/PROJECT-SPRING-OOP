package DO_AN.OOP.repository.ACCOUNT;

import DO_AN.OOP.modal.ACCOUNT.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
