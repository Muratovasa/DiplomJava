package com.example.diplom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JWTToken implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("admin")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var un = userDetails.getUsername();
        return doGenerateToken(claims, un);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

   /* private final SecretKey secret;
    private final int tokenLifetime;
    private final List<String> listTokens = new ArrayList<>();
    private UserEntity userEntity;

    public JWTToken(@Value("${jwt.secret}") String secret, @Value("${TOKEN_LIFETIME}") int tokenLifetime) {
        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.tokenLifetime = tokenLifetime;
    }

    public UserEntity getAuthenticatedUser() {
        return userEntity;
    }

    public String generateToken(@NonNull UserEntity userEntity) throws IllegalArgumentException {
        this.userEntity = userEntity;
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(tokenLifetime)
                .atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .setId(String.valueOf(userEntity.getId()))
                .setSubject(userEntity.getLogin())
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(secret)
                .claim("roles", userEntity.getRoles())
                .compact();
        log.info("Auth-token: {} добавлен в список активных токенов", token);
        listTokens.add(token);
        return token;
    }

    public boolean validateAccessToken(@NonNull String token) {
        for (String t : listTokens) {
            if (!t.equals(token)) {
                return false;
            }
        }
        return validateToken(token, secret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Срок действия токена истек (Token expired)", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Форма токена неподдерживается jwt (Unsupported jwt)", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Форма токена некорректна для jwt (Malformed jwt)", mjEx);
        } catch (SignatureException sEx) {
            log.error("Недействительная подпись (Invalid signature)", sEx);
        } catch (Exception e) {
            log.error("Недопустимый токен (Invalid token)", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, secret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void removeToken(String token) {
        listTokens.remove(token.substring(AuthenticationConfigConstants.TOKEN_PREFIX.length()));
    }*/
}
