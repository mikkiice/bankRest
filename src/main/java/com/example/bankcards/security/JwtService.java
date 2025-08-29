package com.example.bankcards.security;

import com.example.bankcards.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String CLAIM_TYP   = "typ";
    private static final String CLAIM_JTI   = "jti";
    private static final String CLAIM_ROLES = "roles";

    private final JwtProperties jwtProperties;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    /** Генерируем ACCESS: typ=access, добавляем роли */
    public String generateAccessToken(UserDetails user, Map<String, Object> extraClaims) {
        extraClaims.put(CLAIM_TYP, "access");
        extraClaims.put(CLAIM_ROLES, user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return buildToken(user.getUsername(), extraClaims, parseDuration(jwtProperties.getAccessTtl()));
    }

    /** Генерируем REFRESH: typ=refresh, добавляем jti для ротации/отзыва */
    public String generateRefreshToken(UserDetails user, Map<String, Object> extraClaims) {
        extraClaims.put(CLAIM_TYP, "refresh");
        extraClaims.put(CLAIM_JTI, UUID.randomUUID().toString());
        return buildToken(user.getUsername(), extraClaims, parseDuration(jwtProperties.getRefreshTtl()));
    }

    /** Универсальный билдер токена */
    private String buildToken(String username, Map<String, Object> claims, Duration ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(ttl)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // JJWT <= 0.11.x
                .compact();
    }

    /** Разбор токена в структурированный вид (typ/subject/jti/exp) */
    public ParsedToken parse(String token) {
        Claims c = extractAllClaims(token);
        String type = c.get(CLAIM_TYP, String.class);
        String sub  = c.getSubject();
        String jti  = c.get(CLAIM_JTI, String.class); // null для access
        Instant exp = c.getExpiration().toInstant();
        return new ParsedToken(type, sub, jti, exp);
    }

    public record ParsedToken(String type, String subject, String jti, Instant expiresAt) {
        public boolean isAccess()  { return "access".equals(type); }
        public boolean isRefresh() { return "refresh".equals(type); }
    }

    /** Валидация по subject и сроку (подходит для access) */
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get(CLAIM_ROLES, List.class));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Достаём Bearer-токен из заголовка по настройкам header/prefix */
    public String resolveToken(HttpServletRequest request) {
        String headerName = jwtProperties.getHeader();  // например "Authorization"
        String prefix     = jwtProperties.getPrefix();  // например "Bearer "
        String value = request.getHeader(headerName);
        if (value == null || !value.startsWith(prefix)) return null;
        return value.substring(prefix.length());
    }

    /** Парсим строковые TTL вида */
    private Duration parseDuration(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("TTL must not be blank");
        }
        String v = value.trim().toLowerCase();
        long minutes = Long.parseLong(v.substring(0, v.length() - 1));
        if (v.endsWith("m")) {
            return Duration.ofMinutes(minutes);
        } else if (v.endsWith("h")) {
            return Duration.ofHours(minutes);
        } else if (v.endsWith("d")) {
            return Duration.ofDays(minutes);
        } else {
            return Duration.parse(value);
        }
    }
}
