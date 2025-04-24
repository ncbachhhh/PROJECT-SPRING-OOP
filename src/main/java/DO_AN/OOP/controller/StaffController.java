package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.LoginReq;
import DO_AN.OOP.dto.request.StaffCreationReq;
import DO_AN.OOP.dto.request.StaffUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.modal.ACCOUNT.Account;
import DO_AN.OOP.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    ApiResponse<Account> createAccount(@RequestBody StaffCreationReq req) {
        ApiResponse<Account> response = new ApiResponse<>();

        response.setResult(staffService.createAccount(req));

        if (response.getResult() != null) {
            response.setMessage("Account created successfully");
        } else {
            response.setCode(500);
            response.setMessage("Account creation failed");
        }

        return response;
    }

    @GetMapping("/get/users")
    public ApiResponse<List<Account>> getAllAccounts() {
        ApiResponse<List<Account>> response = new ApiResponse<>();
        response.setResult(staffService.getAllAccounts());

        if (response.getResult() != null) {
            response.setMessage("Accounts retrieved successfully");
        } else {
            response.setCode(500);
            response.setMessage("Failed to retrieve accounts");
        }

        return response;
    }

    @GetMapping("/get/user/{accountId}")
    public ApiResponse<Account> getAccountById(@PathVariable("accountId") String accountId) {
        ApiResponse<Account> response = new ApiResponse<>();
        response.setResult(staffService.getAccountById(accountId));

        if (response.getResult() != null) {
            response.setMessage("Account retrieved successfully");
        } else {
            response.setCode(500);
            response.setMessage("Failed to retrieve account");
        }

        return response;
    }

    @PostMapping("/update/{accountId}")
    ApiResponse<Account> updateAccount(@PathVariable("accountId") String accountId, @RequestBody StaffUpdateReq req) {
        ApiResponse<Account> response = new ApiResponse<>();
        Account updatedAccount = staffService.updateAccount(accountId, req);

        if (updatedAccount != null) {
            response.setResult(updatedAccount);
            response.setMessage("Account updated successfully");
        } else {
            response.setCode(500);
            response.setMessage("Account update failed");
        }

        return response;
    }

    @PostMapping("/login")
    ApiResponse<Account> login(@RequestBody LoginReq req) {
        ApiResponse<Account> response = new ApiResponse<>();

        Account account = staffService.login(req);

        if (account != null) {
            response.setResult(account);
            response.setMessage("Login successful");
        } else {
            response.setCode(500);
            response.setMessage("Login failed");
        }
        return response;
    }
}
