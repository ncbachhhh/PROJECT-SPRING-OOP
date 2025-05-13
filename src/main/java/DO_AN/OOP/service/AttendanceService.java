package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.ATTENDANCE.AttendanceRequest;
import DO_AN.OOP.model.ATTENDANCE.Attendance;
import DO_AN.OOP.repository.ATTENDANCE.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    // Thêm mới một bản ghi chấm công (đăng nhập)
    public Attendance clockIn(AttendanceRequest request) {
        Attendance attendance = Attendance.builder()
                .loginTime(LocalDateTime.now())  // Lưu thời gian login (chấm công vào)
                .logoutTime(null)  // Chưa logout, sẽ được cập nhật khi nhân viên ra về
                .accId(request.getAccId())  // accId là ID tài khoản nhân viên
                .build();

        return attendanceRepository.save(attendance);  // Lưu vào cơ sở dữ liệu
    }

    // Cập nhật giờ đăng xuất khi nhân viên ra về
    public void clockOut(String attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi chấm công"));

        attendance.setLogoutTime(LocalDateTime.now());  // Cập nhật giờ logout
        attendanceRepository.save(attendance);
    }

    public double getTotalWorkHoursInMonth(String accId, int month, int year) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        LocalDateTime startOfMonthDateTime = startOfMonth.atStartOfDay();
        LocalDateTime endOfMonthDateTime = endOfMonth.atTime(23, 59, 59); // Bao trọn cả ngày cuối tháng

        List<Attendance> attendances = attendanceRepository.findByAccIdAndLoginTimeBetween(accId, startOfMonthDateTime, endOfMonthDateTime);

        double totalHoursWorked = 0;

        for (Attendance attendance : attendances) {
            if (attendance.getLoginTime() != null && attendance.getLogoutTime() != null) {
                Duration duration = Duration.between(attendance.getLoginTime(), attendance.getLogoutTime());
                double hoursWorked = duration.toMinutes() / 60.0;
                totalHoursWorked += hoursWorked;
            }
        }

        System.out.println("Total hours worked in month: " + totalHoursWorked);
        return totalHoursWorked;
    }
}
