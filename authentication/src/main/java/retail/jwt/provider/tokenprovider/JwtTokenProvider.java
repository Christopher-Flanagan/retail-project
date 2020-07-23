package retail.jwt.provider.tokenprovider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import retail.jwt.properties.JwtProperties;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenProvider {

    private JwtProperties jwtProperties;
    private String secretKey = "secret";
    private long EXPIRATION_TIME;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization ";

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        EXPIRATION_TIME = Long.parseLong(jwtProperties.getDuration());
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getKey().getBytes());
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        return header.substring(7).isEmpty() ? null : header.substring(7);
    }

    public String generateToken( Authentication authentication ) {
        Claims claims = Jwts.claims().setSubject(obtainUsername(authentication));

        return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration(new Date( System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractUsername( String token ) {
        return extractClaim( token, Claims::getSubject);
    }

    public Date extractExpirationData( String token ) {
        return extractClaim( token, Claims::getExpiration);
    }

    private Claims extractAllClaims( String token ) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims( token );
        return claimsResolver.apply(claims);
    }

    private String obtainUsername(Authentication authentication) {
        if(authentication.getPrincipal() instanceof User) {
            return ((User) authentication.getPrincipal()).getUsername();
        }
        else {
            return (String) authentication.getPrincipal();
        }
    }
}
