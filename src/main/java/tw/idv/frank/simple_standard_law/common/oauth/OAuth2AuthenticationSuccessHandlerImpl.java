package tw.idv.frank.simple_standard_law.common.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tw.idv.frank.simple_standard_law.common.redis.service.RedisService;
import tw.idv.frank.simple_standard_law.common.security.service.JwtService;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.RoleFuncMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dao.UsersMapper;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersDetails;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRegisterReq;
import tw.idv.frank.simple_standard_law.schema.system.model.entity.Users;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Value("${domain}")
    private String domain;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleFuncMapper roleFuncMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Start OAuth2 with Google...");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 取得用戶的資料屬性
        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.forEach((key, value) -> {
            log.info("{}: {}", key, value);
        });

        Users user = findUser(attributes);
        String jwt = login(user);
        response.sendRedirect(domain + "/login.html?jwt=" + jwt);
        log.info("End OAuth2 with Google...");
    }

    private Users findUser(Map<String, Object> attributes) {
        Users user = usersMapper.findByAccount((String) attributes.get("email"));
        if(user == null) {
            UsersRegisterReq req = new UsersRegisterReq((String) attributes.get("name"), (String) attributes.get("email"));
            usersMapper.usersRegister(req);
            user = usersMapper.findByUserId(req.getUserId());
        }
        return user;
    }

    private String login(Users user) {
        UsersDetails usersDetails = new UsersDetails(user, roleFuncMapper.findUsersFuncByUsersId(user.getUserId()));
        redisService.addJwtToOnlineList(String.valueOf(user.getUserId()));
        return jwtService.createToken(usersDetails);
    }
}
