package tw.idv.frank.simple_standard_law.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.exception.UserNotFoundException;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.RoleFuncMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersDetails;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Users;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RoleFuncMapper roleFuncMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Users users = usersMapper.findByAccount(account);

        if (Objects.isNull(users)) {
            throw new UserNotFoundException(String.format("找不到此帳號: %s", account));
        }

        return new UsersDetails(users, roleFuncMapper.findUsersFuncByUsersId(users.getUserId()));
    }
}
