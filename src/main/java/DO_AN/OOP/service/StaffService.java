package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.ACCOUNT.LoginReq;
import DO_AN.OOP.dto.request.ACCOUNT.StaffCreationReq;
import DO_AN.OOP.dto.request.ACCOUNT.StaffUpdateReq;
import DO_AN.OOP.dto.request.ATTENDANCE.AttendanceRequest;
import DO_AN.OOP.model.ACCOUNT.*;
import DO_AN.OOP.repository.ACCOUNT.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StaffService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    Tạo mới tài khoản
    public Account createAccount(StaffCreationReq req) {
        if (accountRepository.existsByPhone(req.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        Account account = switch (req.getRole()) {
            case CHEF -> Chef.builder().build();
            case MANAGER -> Manager.builder().build();
            case CASHIER -> Cashier.builder().build();
            case CUSTOMER -> Customer.builder().point(0).build();
            default -> throw new RuntimeException("Role không hợp lệ để tạo staff");
        };

        account.setUsername(req.getUsername());
        account.setPassword(passwordEncoder.encode(req.getPassword()));
        account.setStatus("Đang hoạt động");
        account.setRole(req.getRole());
        account.setAddress(req.getAddress());
        account.setPhone(req.getPhone());
        account.setEmail(req.getEmail());
        account.setCreatedAt(LocalDate.now());

        return accountRepository.save(account);
    }

    //    Lấy danh sách tài khoản
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            throw new RuntimeException("Không tồn tại tài khoản nào");
        }

        return accounts;
    }

    //    Lấy tài khoản theo id
    public Account getAccountById(String id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    //    Cập nhật tài khoản
    public Account updateAccount(String id, StaffUpdateReq req) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (accountRepository.existsByPhone(req.getPhone()) && !account.getPhone().equals(req.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        account.setPassword(req.getUsername());
        account.setPhone(req.getPhone());
        account.setEmail(req.getEmail());
        account.setAddress(req.getAddress());
        account.setRole(req.getRole());
        account.setStatus(req.getStatus());

        return accountRepository.save(account);
    }

    //    Xóa tài khoản
    @Transactional
    public void deleteAccount(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        account.setStatus("Bị khóa");

        accountRepository.save(account);
    }

    //   Đăng nhập tài khoản
    public Account login(LoginReq req) {
        Account account = accountRepository.findByPhone(req.getPhone()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (!passwordEncoder.matches(req.getPassword(), account.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        if (!account.getStatus().equals("Đang hoạt động")) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        // Tạo Attendance khi login thành công (chấm công vào)
        AttendanceRequest attendanceRequest = new AttendanceRequest(account.getId());  // Truyền accId vào request
        attendanceService.clockIn(attendanceRequest);

        return account;
    }
}
