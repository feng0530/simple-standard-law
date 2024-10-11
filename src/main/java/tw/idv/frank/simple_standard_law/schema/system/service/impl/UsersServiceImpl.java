package tw.idv.frank.simple_standard_law.schema.system.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.common.dto.LoginRes;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.service.JwtService;
import tw.idv.frank.simple_standard_law.common.service.RedisService;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersDetails;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRegisterReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Users;
import tw.idv.frank.simple_standard_law.schema.system.service.UsersService;

import java.rmi.server.RemoteRef;
import java.util.List;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisService redisService;

    @Override
    public UsersRes usersRegister(UsersRegisterReq req) throws BaseException {
        return register(req);
    }

    @Override
    public LoginRes usersLogin(LoginReq req) {
        UsersDetails usersDetails = authentication(req);
//        return new LoginRes(jwtService.createToken(usersDetails));
        String jwt = jwtService.createToken(usersDetails);
        String userId = String.valueOf(usersDetails.getUsers().getUserId());
        redisService.addJwtToOnlineList(userId);
        return  new LoginRes(jwt);
    }

    @Override
    public void usersLogout(HttpServletRequest http) {
        String jwt = http.getHeader("Authorization").substring("Bearer ".length());
        Claims claims = jwtService.parseToken(jwt);
        redisService.deleteJwtInOnlineList(claims.getId());
    }

    @Override
    public List<UsersRes> findUsersList() {
        return usersMapper.findUsersList()
                .stream()
                .map(user -> modelMapper.map(user, UsersRes.class))
                .toList();
    }

    @Override
    public UsersRes findByUserId(Integer userId) throws BaseException {
        Users users = usersMapper.findByUserId(userId);
        if (users == null) {
            throw new BaseException(CommonCode.ERROR_904);
        }
        return modelMapper.map(users, UsersRes.class);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        usersMapper.deleteByUserId(userId);
    }

    private UsersDetails authentication(LoginReq req) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getAccount(), req.getPassword());
        authentication = provider.authenticate(authentication);
        UsersDetails userDetails = (UsersDetails)authentication.getPrincipal();
        return userDetails;
    }

    private UsersRes register(UsersRegisterReq req) {
        req.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
        usersMapper.usersRegister(req);
        return modelMapper.map(usersMapper.findById(req.getUserId()), UsersRes.class);
    }

    private void validAccountExist(String account) throws BaseException {
        Users users = usersMapper.findByAccount(account);

        if (users != null) {
            log.error("新增失敗: {}", CommonCode.U901.getMes());
            throw new BaseException(CommonCode.U901);
        }
    }
}
