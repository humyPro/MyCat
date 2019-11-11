package com.humy.mycat.interceptor.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:43
 * @Description:
 */
@Slf4j
public class Jwt {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 3600 * 1000 * 1000;

    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    public static final String jwtId = "tokenId";

    public static final String USER_ID = "userId";

    /**
     * 创建JWT
     */
    public static String createJWT(Long userId) {
        Objects.requireNonNull(userId);
        Key key = getKey();
        JwtBuilder builder = Jwts.builder().setSubject(userId.toString()).signWith(key).setIssuedAt(new Date());
        return builder.compact();
    }

    /**
     * 验证jwt
     */
    public static boolean verifyJwt(String token) {
        return !(getClaims(token) == null);
    }

    public static Claims getClaims(String token) {
        if (token == null)
            return null;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception ex) {
            log.debug("token expired");
        }
        return null;
    }

    private static Key getKey() {
        return new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

}
