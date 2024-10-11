package tw.idv.frank.simple_standard_law.common.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.idv.frank.simple_standard_law.common.service.JwtService;
import tw.idv.frank.simple_standard_law.common.service.RedisService;
import tw.idv.frank.simple_standard_law.common.tools.JsonTool;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersFunc;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        String requestURI = request.getRequestURI();
//        // 如果是某些特定的 URL，則不執行驗證
//        if("/".equals(requestURI)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 取得 request header 的值
        String access = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (access != null) {
            String accessToken = access.replace("Bearer ", "");

            // 解析 token
            Claims tokenPayload = jwtService.parseToken(accessToken);
            if(!tokenPayload.isEmpty()){

                UsersRes usersRes = modelMapper.map(tokenPayload.get("user"), UsersRes.class);

                // 從 Redis取出 authorities
                List<UsersFunc> usersFuncList = JsonTool.convertJsonToObject(redisService.getAuthorities(String.valueOf(usersRes.getUserId())), new TypeReference<List<UsersFunc>>() {});
                List<GrantedAuthority> authorities = usersFuncList.stream()
                        .flatMap(usersFunc -> usersFunc.getAuthorities().stream())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // 將使用者身份與權限傳遞給 Spring Security
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        usersRes.getUserId(),
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 將 request 送往 Controller 或下一個 filter
        filterChain.doFilter(request, response);
    }
}