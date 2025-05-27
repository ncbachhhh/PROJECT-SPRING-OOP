package DO_AN.OOP.dto.request.ACCOUNT;

import DO_AN.OOP.model.ACCOUNT.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffUpdateReq {
    private String username;
    private String address;
    private String phone;
    private String email;
    private Role role;
    private String status;
}
