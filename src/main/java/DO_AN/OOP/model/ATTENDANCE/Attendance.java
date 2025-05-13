package DO_AN.OOP.model.ATTENDANCE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;  // ID chấm công

    private LocalDateTime loginTime;  // Thời gian đăng nhập
    private LocalDateTime logoutTime; // Thời gian đăng xuất

    private String accId;  // ID tài khoản nhân viên
}
