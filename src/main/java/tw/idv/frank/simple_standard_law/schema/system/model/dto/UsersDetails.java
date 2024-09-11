package tw.idv.frank.simple_standard_law.schema.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tw.idv.frank.simple_standard_law.common.tools.JsonTool;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDetails implements UserDetails {

    private Users users;

    private List<UsersFunc> usersFuncList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.usersFuncList.stream()
                .flatMap(usersFunc -> usersFunc.getAuthorities().stream())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

//        return this.authorities.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.users.getPassword();
    }

    @Override
    public String getUsername() {
        return this.users.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
