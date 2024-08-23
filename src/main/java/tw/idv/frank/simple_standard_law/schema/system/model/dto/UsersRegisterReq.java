package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsersRegisterReq {
    // 用來接收自增主鍵的屬性
    private Integer userId;

    @NotBlank
    @JsonProperty("userName")
    private String userName;

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
