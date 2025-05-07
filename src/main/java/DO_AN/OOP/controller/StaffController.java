package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.ACCOUNT.LoginReq;
import DO_AN.OOP.dto.request.ACCOUNT.StaffCreationReq;
import DO_AN.OOP.dto.request.ACCOUNT.StaffUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.ACCOUNT.Account;
import DO_AN.OOP.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // Tạo tài khoản nhân viên
    @PostMapping("/create")
    public ApiResponse<Account> createAccount(@RequestBody StaffCreationReq req) {
        ApiResponse<Account> response = new ApiResponse<>();
        try {
            Account account = staffService.createAccount(req);
            response.setMessage("Tạo tài khoản thành công");
            response.setResult(account);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Tạo tài khoản thất bại: " + e.getMessage());
        }
        return response;
    }

    // Lấy tất cả tài khoản
    @GetMapping("/get/users")
    public ApiResponse<List<Account>> getAllAccounts() {
        ApiResponse<List<Account>> response = new ApiResponse<>();
        try {
            List<Account> accounts = staffService.getAllAccounts();
            response.setMessage("Lấy danh sách tài khoản thành công");
            response.setResult(accounts);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Không thể lấy danh sách tài khoản: " + e.getMessage());
        }
        return response;
    }

    // Lấy tài khoản theo ID
    @GetMapping("/get/user/{accountId}")
    public ApiResponse<Account> getAccountById(@PathVariable("accountId") String accountId) {
        ApiResponse<Account> response = new ApiResponse<>();
        try {
            Account account = staffService.getAccountById(accountId);
            response.setMessage("Lấy tài khoản thành công");
            response.setResult(account);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Không tìm thấy tài khoản: " + e.getMessage());
        }
        return response;
    }

    // Cập nhật thông tin tài khoản
    @PostMapping("/update/{accountId}")
    public ApiResponse<Account> updateAccount(@PathVariable("accountId") String accountId,
                                              @RequestBody StaffUpdateReq req) {
        ApiResponse<Account> response = new ApiResponse<>();
        try {
            Account updatedAccount = staffService.updateAccount(accountId, req);
            response.setMessage("Cập nhật tài khoản thành công");
            response.setResult(updatedAccount);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật tài khoản thất bại: " + e.getMessage());
        }
        return response;
    }

    // Đăng nhập
    @PostMapping("/login")
    public ApiResponse<Account> login(@RequestBody LoginReq req) {
        ApiResponse<Account> response = new ApiResponse<>();
        try {
            Account account = staffService.login(req);
            response.setMessage("Đăng nhập thành công");
            response.setResult(account);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Đăng nhập thất bại: " + e.getMessage());
        }
        return response;
    }
}
