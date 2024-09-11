package tw.idv.frank.simple_standard_law.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.expiration}")
    private Long expirationMillis;

    private static final String BLACKLIST_PREFIX = "blacklist:";
    private static final String AUTHORITIES_PREFIX = "authorities:";

    public void addJwtToBlackList(String jwt) {
        String key = BLACKLIST_PREFIX + jwt;
        Duration expirationDuration = Duration.ofMillis(expirationMillis);
        redisTemplate.opsForValue().set(key, "disable", expirationDuration);
    }

    public boolean isJwtInBlackList(String jwt) {
        String key = BLACKLIST_PREFIX + jwt;
        return redisTemplate.opsForValue().get(key) != null;
    }

    public void addAuthorities(String userId, String authorities) {
        String key = AUTHORITIES_PREFIX + userId;
        Duration expirationDuration = Duration.ofMillis(expirationMillis);
        redisTemplate.opsForValue().set(key, authorities, expirationDuration);
    }

    public String getAuthorities(String userId) {
        String key = AUTHORITIES_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }
}