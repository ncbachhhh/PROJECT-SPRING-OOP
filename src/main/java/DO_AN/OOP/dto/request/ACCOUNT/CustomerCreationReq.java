package DO_AN.OOP.dto.request.ACCOUNT;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreationReq {
    private String username;
    private String password;
    private String address;
    private String phone;
    private String email;
}