package DO_AN.OOP.repository.ACCOUNT;

import DO_AN.OOP.model.ACCOUNT.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByPhone(String phone);

    Optional<Account> findByPhone(String phone);
}
