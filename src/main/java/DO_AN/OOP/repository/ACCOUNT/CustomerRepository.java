package DO_AN.OOP.repository.ACCOUNT;

import DO_AN.OOP.model.ACCOUNT.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByPhone(String phone);
}