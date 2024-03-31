package com.legit.housing.config;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class AuditorProvider implements AuditorAware<String> {
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        var securityCtx = SecurityContextHolder.getContext().getAuthentication();

        if (securityCtx != null) {
            if (securityCtx.getPrincipal().equals("anonymousUser"))
                return securityCtx.getPrincipal().toString().describeConstable();

            var principal = (Jwt) securityCtx.getPrincipal();
            return principal.getSubject().describeConstable();
        }

        return Optional.of("");
    }
}
