package fullstuck.green.wallet.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import fullstuck.green.wallet.Model.Entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${app.greenwallet.jwt.app.name")
    private String appName;
//    @Value("${app.greenwallet.jwt.expired")
    @Value("#{new Long('${app.greenwallet.jwt.expired}')}")
    private long jwtExpiration;
    @Value("app.greenwallet.jwt.jwt-secret")
    private String jwtSecret;
    /**
     * dynamic jwt secret: get from api gateway
     */

    public String generateToken(AppUser appUser) {
//        String jwtSecret = "secret";
        System.out.println("genrate token");
        // algorithm based on jwt secret
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        System.out.println("algorithm: " + algorithm);
        return JWT.create()
                // issuer
                .withIssuer(appName)
                .withSubject(appUser.getId())
                // expiredat static
                .withExpiresAt(Instant.now().plusSeconds(jwtExpiration))
                .withIssuedAt(Instant.now())
                .withClaim("role", appUser.getRole().name())
                .sign(algorithm);
    }

    public boolean verifyJwtToken(String token) {
//        String jwtSecret = "secret";
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getIssuer().equals(appName);
    }

    public Map<String, String> getUserInfoByToken(String token) {
//        String jwtSecret = "secret";
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }
    }
}
