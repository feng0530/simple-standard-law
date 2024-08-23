package tw.idv.frank.simple_standard_law.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
