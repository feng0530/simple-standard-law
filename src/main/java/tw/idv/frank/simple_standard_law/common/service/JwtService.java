package tw.idv.frank.simple_standard_law.common.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import tw.idv.frank.simple_standard_law.common.tools.JsonTool;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersDetails;
import tw.idv.frank.simple_standard_law.schema.system.model.dto.UsersRes;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private Long expirationMillis;

    // JWT 金鑰
    private Key secretKey;

    // JWT 解析器
    private JwtParser jwtParser;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisService redisService;

    @PostConstruct
    private void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jwtParser = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build();
    }

    public Claims parseToken(String jwt) {

        try {
            if (redisService.isJwtInBlackList(jwt)) {
                log.error("JWT ID無效，表示已經登出或者已經被鎖定");
                return Jwts.claims();
            }
            return jwtParser.parseClaimsJws(jwt).getBody();
        }catch (SignatureException e) {
            log.error("JWT 的簽名無效，表示JWT 被竄改或者使用了錯誤的密鑰驗證");
        }
        catch (MalformedJwtException e) {
            log.error("JWT 的格式錯誤，可能是JWT 字符串被修改或者製作 JWT 的過程中發生了錯誤");
        }
        catch (ExpiredJwtException e) {
            log.error("JWT 已過期");
        }
        catch (UnsupportedJwtException e) {
            log.error("不支持的JWT，表示header 中指定的加密算法不被支持");
        }
        catch (IllegalArgumentException e) {
            log.error("表示提供了無效的JWT 字串");
        }
        return Jwts.claims();
    }

    public String createToken(UsersDetails usersDetails) {
        String jti = String.valueOf(UUID.randomUUID());
        UsersRes usersRes = modelMapper.map(usersDetails.getUsers(), UsersRes.class);

        redisService.addAuthorities(String.valueOf(usersRes.getUserId()), JsonTool.toJson(usersDetails.getUsersFuncList()));

        return Jwts.builder()
                .setId(jti)
                .setSubject("Access Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .claim("user", usersRes)
//                .claim("authorities", usersDetails.getUsersFuncList())
                .signWith(secretKey)
                .compact();
    }
}
