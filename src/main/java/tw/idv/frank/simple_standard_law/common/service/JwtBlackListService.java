package tw.idv.frank.simple_standard_law.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtBlackListService {

    @Value("${jwt.expiration}")
    private Long expirationMillis;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    public void addJwtToBlackList(String jwt) {
//        String key = BLACKLIST_PREFIX + jwt;
        Duration expirationDuration = Duration.ofMillis(expirationMillis);
        redisTemplate.opsForValue().set(jwt, "disable", expirationDuration);
    }

    public boolean isJwtInBlackList(String jwt) {
//        String key = BLACKLIST_PREFIX + jwt;
        return redisTemplate.opsForValue().get(jwt) != null;
    }
}