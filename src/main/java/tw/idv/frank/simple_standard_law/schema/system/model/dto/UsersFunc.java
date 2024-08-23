package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UsersFunc implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
