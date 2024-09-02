package tw.idv.frank.simple_standard_law.common.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersFunc;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

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

                List<LinkedHashMap<String, String>> authoritiesMaps = (List<LinkedHashMap<String, String>>) tokenPayload.get("authorities");

                List<GrantedAuthority> authorities = authoritiesMaps.stream()
                        .flatMap(map -> map.values().stream())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                Integer userId = (Integer) tokenPayload.get("userId");

                // 將使用者身份與權限傳遞給 Spring Security
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userId,
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