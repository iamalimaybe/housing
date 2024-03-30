package com.legit.housing.service.impl;

import com.legit.housing.dto.response.JwtRes;
import com.legit.housing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.token.expiry:10}")
    private Long tokenExpiry;

    @Autowired
    private JwtEncoder jwtEncoder;
    @Override
    public JwtRes generateToken(Authentication authentication) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder().
                issuer("self").
                issuedAt(now).
                expiresAt(now.plus(tokenExpiry, ChronoUnit.SECONDS)).
                subject(authentication.getName()).
                build();

        var token = jwtEncoder.encode(
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claimsSet)
        ).getTokenValue();

        return JwtRes.builder().accessToken(token).expiresIn(claimsSet.getExpiresAt().getEpochSecond()).build();
    }
}
