package com.fcprovin.api.config.jwt;

import com.fcprovin.api.dto.jwt.JwtCreate;
import com.fcprovin.api.dto.jwt.JwtRole;
import com.fcprovin.api.dto.jwt.JwtToken;
import com.fcprovin.api.dto.jwt.JwtType;
import com.fcprovin.api.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

import static com.fcprovin.api.dto.jwt.JwtType.ACCESS;
import static com.fcprovin.api.dto.jwt.JwtType.REFRESH;
import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.lang.Assert.notNull;
import static java.lang.String.join;
import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.security.key}")
    private String key;

    @Value("${jwt.expire.access}")
    private Long accessExpire;

    @Value("${jwt.expire.refresh}")
    private Long refreshExpire;

    private final JwtUserDetailsService userDetailsService;
    private final TokenService tokenService;

    public JwtCreate createJwt(JwtToken token) {
        LocalDateTime now = now();
        LocalDateTime expire = now.plusHours(expireHour(token.getType()));
        SecretKey secretKey = getSecretKey();

        return JwtCreate.builder()
                .token(Jwts.builder()
                        .subject(String.valueOf(token.getSubject()))
                        .audience()
                            .add(token.getScope().name())
                            .and()
                        .issuer(token.getType().name())
                        .issuedAt(toDate(now))
                        .expiration(toDate(expire))
                        .signWith(secretKey)
                        .compact())
                .expire(expire)
                .build();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public void validateToken(String jwt) {
        notNull(jwt, "Token is null");

        Claims claims = getClaims(jwt);
        JwtToken token = getToken(claims);

        if (validateExpire(claims)) {
            throw new IllegalArgumentException("Token is expired");
        }

        if (REFRESH.equals(token.getType())) {
            tokenService.validateRefreshToken(token, jwt);
        }
    }

    private Claims getClaims(String jwt) {
        return parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private boolean validateExpire(Claims claims) {
        return claims.getExpiration().before(toDate(now()));
    }

    private String getUsername(String jwt) {
        Claims claims = getClaims(jwt);

        return join("/t", claims.getSubject(), getAudience(claims));
    }

    private JwtToken getToken(Claims claims) {
        return JwtToken.builder()
                .subject(Long.valueOf(claims.getSubject()))
                .scope(JwtRole.valueOf(getAudience(claims)))
                .type(JwtType.valueOf(claims.getIssuer()))
                .build();
    }

    private String getAudience(Claims claims) {
        return claims.getAudience().stream().findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not exists audience"));
    }

    private long expireHour(JwtType type) {
        return ACCESS.equals(type) ? accessExpire : refreshExpire;
    }

    private Date toDate(LocalDateTime time) {
        return valueOf(time);
    }
}
