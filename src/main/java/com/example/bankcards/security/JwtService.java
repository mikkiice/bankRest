package com.example.bankcards.security;

import com.example.bankcards.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
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

    private final JwtProperties props;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.getSecret()));
    }

    public String generateAccessToken(UserDetails user, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        Instant exp = now.plus(props.getAccessTtl());
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(props.getIssuer())
                .setAudience(props.getAudience())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .addClaims(extraClaims == null ? Map.of() : extraClaims)
                .claim(CLAIM_TYP, "access")
                .claim(CLAIM_JTI, UUID.randomUUID().toString())
                .claim(CLAIM_ROLES, roles)
                .signWith(getSignKey(), SignatureAlgorithm.HS256);

        return builder.compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(parseAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get(CLAIM_ROLES, List.class));
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String username = extractUsername(token);
        Date exp = extractClaim(token, Claims::getExpiration);
        return username.equals(user.getUsername()) && exp.after(new Date());
    }

    private Claims parseAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .requireIssuer(props.getIssuer())
                .requireAudience(props.getAudience())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}