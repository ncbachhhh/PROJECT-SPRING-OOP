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
public class StaffCreationReq {
    private String username;
    private String password;
    private Role role;
    private String address;
    private String phone;
    private String email;
}
