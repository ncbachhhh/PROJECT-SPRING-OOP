package DO_AN.OOP.controller;

import DO_AN.OOP.repository.ACCOUNT.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AccountRepository accountRepository;

    @DeleteMapping("/reset-db")
    public String resetDatabase() {
        //  Xóa tất cả các bản ghi trong bảng tài khoản
        accountRepository.deleteAll();
        return "Database reset successfully";
    }
}
