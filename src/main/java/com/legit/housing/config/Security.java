package com.legit.housing.config;

import com.legit.housing.service.UserService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

import static com.legit.housing.entity.main.Enum.Permission.*;
import static com.legit.housing.entity.main.Enum.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class Security {
    @Value("${jwt.secret.key}")
    private String secret;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Autowired
    private UserService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(auth -> auth
                        .requestMatchers(POST, "/api/v1/token").permitAll()
                        .requestMatchers(POST, "/api/v1/register").anonymous()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/error/**").permitAll()

                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                        .requestMatchers("/api/v1/tenant/**").hasAnyRole(ADMIN.name(), MANAGER.name(), TENANT.name())
                        .requestMatchers(GET, "/api/v1/tenant/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name(), TENANT_READ.name())
                        .requestMatchers(POST, "/api/v1/tenant/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name(), TENANT_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/tenant/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name(), TENANT_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/tenant/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name(), TENANT_DELETE.name())

                        .requestMatchers("/api/v1/landlord/**").hasAnyRole(ADMIN.name(), MANAGER.name(), LANDLORD.name())
                        .requestMatchers(GET, "/api/v1/landlord/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name(), LANDLORD_READ.name())
                        .requestMatchers(POST, "/api/v1/landlord/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name(), LANDLORD_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/landlord/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name(), LANDLORD_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/landlord/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name(), LANDLORD_DELETE.name())

                        .requestMatchers("/api/v1/agent/**").hasAnyRole(ADMIN.name(), MANAGER.name(), AGENT.name())
                        .requestMatchers(GET, "/api/v1/agent/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name(), AGENT_READ.name())
                        .requestMatchers(POST, "/api/v1/agent/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name(), AGENT_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/agent/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name(), AGENT_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/agent/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name(), AGENT_DELETE.name())

                        .anyRequest().authenticated()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint)).
                oauth2ResourceServer(configure -> configure.jwt(jwt -> jwt.decoder(jwtDecoder()))).
                httpBasic(Customizer.withDefaults()).
                userDetailsService(userDetailsService).
                build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec originalKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKeySpec originalKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");
        JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(originalKey);
        return new NimbusJwtEncoder(immutableSecret);
    }

    @Bean
    public AuthenticationManager authManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
