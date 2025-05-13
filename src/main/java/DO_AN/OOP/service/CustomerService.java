package DO_AN.OOP.service;


import DO_AN.OOP.dto.request.ACCOUNT.CustomerCreationReq;
import DO_AN.OOP.dto.request.ACCOUNT.LoginReq;
import DO_AN.OOP.model.ACCOUNT.Customer;
import DO_AN.OOP.model.ACCOUNT.Role;
import DO_AN.OOP.repository.ACCOUNT.AccountRepository;
import DO_AN.OOP.repository.ACCOUNT.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer createCustomer(CustomerCreationReq req) {
        // Kiểm tra số điện thoại đã tồn tại chưa
        if (accountRepository.existsByPhone(req.getPhone())) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }

        // Tạo mới Customer
        Customer customer = Customer.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .address(req.getAddress())
                .phone(req.getPhone())
                .email(req.getEmail())
                .status("Đang hoạt động")
                .role(Role.CUSTOMER)
                .createdAt(LocalDate.now())
                .point(0)
                .build();

        return customerRepository.save(customer);
    }

    public Customer login(LoginReq req) {
        Customer customer = customerRepository.findByPhone(req.getPhone())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản khách hàng"));

        if (!passwordEncoder.matches(req.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        if (!"Đang hoạt động".equals(customer.getStatus())) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        return customer;
    }

}
