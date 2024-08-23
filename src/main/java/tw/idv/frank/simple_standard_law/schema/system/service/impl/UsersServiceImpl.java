package tw.idv.frank.simple_standard_law.schema.system.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.LoginReq;
import tw.idv.frank.simple_standard_law.common.dto.LoginRes;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.filter.JwtFilter;
import tw.idv.frank.simple_standard_law.common.service.JwtBlackListService;
import tw.idv.frank.simple_standard_law.common.service.JwtService;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersDetails;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRegisterReq;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Users;
import tw.idv.frank.simple_standard_law.schema.system.service.UsersService;

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
    private JwtBlackListService jwtBlackListService;

    @Override
    public UsersRes usersRegister(UsersRegisterReq req) throws BaseException {
        validAccountExist(req.getAccount());
        return register(req);
    }

    @Override
    public LoginRes usersLogin(LoginReq req) {
        UsersDetails usersDetails = authentication(req);
        return new LoginRes(jwtService.createToken(usersDetails));
    }

    @Override
    public void usersLogout(HttpServletRequest req) {
        String jwt = req.getHeader("Authorization").substring("Bearer ".length());
        jwtBlackListService.addJwtToBlackList(jwt);
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