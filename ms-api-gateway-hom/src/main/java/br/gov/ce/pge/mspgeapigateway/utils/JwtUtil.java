package br.gov.ce.pge.mspgeapigateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${api.secutiry.token.secret}")
    private String secretKey;

    public Object validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("ms-pge-oauth")
                    .build()
                    .verify(token)
                    .getClaims().get("permissoes");
        } catch(JWTVerificationException ex) {
            return "";
        }
    }

}
