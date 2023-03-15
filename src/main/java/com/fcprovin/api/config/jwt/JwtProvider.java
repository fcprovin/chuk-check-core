package com.fcprovin.api.config.jwt;

import com.fcprovin.api.dto.jwt.JwtCreate;
import com.fcprovin.api.dto.jwt.JwtRole;
import com.fcprovin.api.dto.jwt.JwtToken;
import com.fcprovin.api.dto.jwt.JwtType;
import com.fcprovin.api.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

import static com.fcprovin.api.dto.jwt.JwtType.ACCESS;
import static com.fcprovin.api.dto.jwt.JwtType.REFRESH;
import static io.jsonwebtoken.Jwts.parser;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
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

        return JwtCreate.builder()
                .token(Jwts.builder()
                        .setSubject(String.valueOf(token.getSubject()))
                        .setAudience(token.getScope().name())
                        .setIssuer(token.getType().name())
                        .setIssuedAt(toDate(now))
                        .setExpiration(toDate(expire))
                        .signWith(HS256, key)
                        .compact())
                .expire(expire)
                .build();
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
        return parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    private boolean validateExpire(Claims claims) {
        return claims.getExpiration().before(toDate(now()));
    }

    private String getUsername(String jwt) {
        Claims claims = getClaims(jwt);

        return join("/t", claims.getSubject(), claims.getAudience());
    }

    private JwtToken getToken(Claims claims) {
        return JwtToken.builder()
                .subject(Long.valueOf(claims.getSubject()))
                .scope(JwtRole.valueOf(claims.getAudience()))
                .type(JwtType.valueOf(claims.getIssuer()))
                .build();
    }

    private long expireHour(JwtType type) {
        return ACCESS.equals(type) ? accessExpire : refreshExpire;
    }

    private Date toDate(LocalDateTime time) {
        return valueOf(time);
    }
}
