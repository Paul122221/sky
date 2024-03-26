package com.paul.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class TokenController {

    @Autowired
    JwtEncoder encoder;

    /**
     * Endpoint to generate JWT token.
     * @param authentication The authentication object containing user details.
     * @return The generated JWT token.
     */
    @PostMapping("/token")
    public String token(Authentication authentication) {
        log.info("Request received to generate JWT token.");

        Instant now = Instant.now();
        long expiry = 36000L;
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        String generatedToken = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        log.info("JWT token generated successfully.");
        return generatedToken;
    }
}
