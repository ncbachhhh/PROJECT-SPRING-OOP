package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.ATTENDANCE.AttendanceRequest;
import DO_AN.OOP.model.ATTENDANCE.Attendance;
import DO_AN.OOP.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Đăng nhập (chấm công vào)
    @PostMapping("/clock-in")
    public Attendance clockIn(@RequestBody AttendanceRequest request) {
        return attendanceService.clockIn(request);
    }

    // Đăng xuất (chấm công ra)
    @PostMapping("/clock-out/{attendanceId}")
    public void clockOut(@PathVariable String attendanceId) {
        attendanceService.clockOut(attendanceId);
    }

    // Lấy tổng số giờ làm việc trong tháng
    @GetMapping("/total-hours/{accId}")
    public double getTotalWorkHoursInMonth(
            @PathVariable String accId,
            @RequestParam int month,
            @RequestParam int year) {

        return attendanceService.getTotalWorkHoursInMonth(accId, month, year);
    }
}
