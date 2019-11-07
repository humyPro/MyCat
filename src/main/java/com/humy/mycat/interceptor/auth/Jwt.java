package com.humy.mycat.interceptor.auth;

import com.humy.mycat.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: Milo Hu
 * @Date: 11/7/2019 10:43
 * @Description:
 */
public class Jwt {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;

    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    public static final String jwtId = "tokenId";

    /**
     * 创建JWT
     */
    public static String createJWT(User user) {
        HashMap<String, Object> claims = new HashMap<>(2);
        claims.put("id", user.getId());
        claims.put("nickName", user.getNickName());
        claims.put("temNumber", user.getTelNumber());
        Key key = getKey();
        JwtBuilder builder = Jwts.builder().setClaims(claims).setSubject(user.getTelNumber()).signWith(key).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000));
        return builder.compact();
    }

    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }

    private static Key getKey() {
        return new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

}
