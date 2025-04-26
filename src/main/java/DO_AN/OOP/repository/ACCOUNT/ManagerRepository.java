package DO_AN.OOP.repository.ACCOUNT;

import DO_AN.OOP.model.ACCOUNT.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, String> {
}
