package DO_AN.OOP.dto.request.ATTENDANCE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {
    private String accId;  // ID tài khoản nhân viên
}
