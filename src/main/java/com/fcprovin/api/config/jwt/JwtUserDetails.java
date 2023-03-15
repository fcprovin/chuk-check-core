package com.fcprovin.api.config.jwt;

import com.fcprovin.api.dto.jwt.JwtRole;
import com.fcprovin.api.dto.request.create.TokenCreateRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.lang.String.valueOf;
import static java.util.Collections.singletonList;

@Getter
public class JwtUserDetails implements UserDetails {

    private final Long subject;
    private final JwtRole scope;

    @Builder
    public JwtUserDetails(Long subject, JwtRole scope) {
        this.subject = subject;
        this.scope = scope;
    }

    @Override
    public String getUsername() {
        return valueOf(subject);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singletonList(new SimpleGrantedAuthority(scope.name()));
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException();
    }

    public TokenCreateRequest toRequest() {
        return TokenCreateRequest.builder()
                .subject(subject)
                .scope(scope)
                .build();
    }
}
