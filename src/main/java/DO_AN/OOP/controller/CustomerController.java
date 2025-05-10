package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.ACCOUNT.CustomerCreationReq;
import DO_AN.OOP.dto.request.ACCOUNT.LoginReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.ACCOUNT.Customer;
import DO_AN.OOP.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Đăng ký tài khoản khách hàng
    @PostMapping("/register")
    public ApiResponse<Customer> registerCustomer(@RequestBody CustomerCreationReq req) {
        ApiResponse<Customer> response = new ApiResponse<>();
        try {
            Customer customer = customerService.createCustomer(req);
            response.setMessage("Đăng ký tài khoản thành công");
            response.setResult(customer);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Đăng ký thất bại: " + e.getMessage());
        }
        return response;
    }
    
    // Đăng nhập tài khoản khách hàng
    @PostMapping("/login")
    public ApiResponse<Customer> loginCustomer(@RequestBody LoginReq req) {
        ApiResponse<Customer> response = new ApiResponse<>();
        try {
            Customer customer = customerService.login(req);
            response.setMessage("Đăng nhập thành công");
            response.setResult(customer);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Đăng nhập thất bại: " + e.getMessage());
        }
        return response;
    }
}
