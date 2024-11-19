package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public UsersRegisterReq(String userName, String account) {
        this.userName = userName;
        this.account = account;
        this.password = "OAuthTest";
    }

    @Override
    public String toString() {
        return "UsersRegisterReq{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='[PROTECTED]'" +  // 過濾密碼
                '}';
    }
}
