package com.why.easyevent.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 16 08
 * @Version: v1.0
 */
public class TokenUtils {
    static long MILLI_SECONDS_IN_HOUR = 1 * 60 * 60 * 1000;
    static String ISSUER = "itwhy";
    static String USER_ID = "userId";
    static Algorithm algorithm = Algorithm.HMAC256("mysecretkey");

    public static String signToken(Integer userId, int expirationInHour) {
        String token = JWT.create()
                .withIssuer(ISSUER)
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationInHour * MILLI_SECONDS_IN_HOUR ))
                .sign(algorithm);
        return token;
    }

    public static Integer verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Integer userId = jwt.getClaim(USER_ID).asInt();
        return userId;
    }

}
