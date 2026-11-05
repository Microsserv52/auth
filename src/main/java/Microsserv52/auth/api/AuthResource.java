package Microsserv52.auth.api;

import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    private final AccountClient accountClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final String internalServiceSecret;
    private final long expirationSeconds;

    public AuthResource(AccountClient accountClient, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder,
                        @Value("${platform.internal-service-secret}") String internalServiceSecret,
                        @Value("${platform.access-token-expiration-seconds:900}") long expirationSeconds) {
        this.accountClient = accountClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.internalServiceSecret = internalServiceSecret;
        this.expirationSeconds = expirationSeconds;
    }

    @PostMapping("/login")
    public TokenOut login(@Valid @RequestBody LoginIn input) {
        AccountClient.Credentials account;
        try {
            account = accountClient.credentials(input.email().toLowerCase(), internalServiceSecret);
        } catch (FeignException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        if (!account.enabled() || !passwordEncoder.matches(input.password(), account.passwordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        Instant issuedAt = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("microsserv52-auth")
            .issuedAt(issuedAt)
            .expiresAt(issuedAt.plusSeconds(expirationSeconds))
            .subject(account.id().toString())
            .claim("email", account.email())
            .claim("roles", Arrays.stream(account.roles().split(","))
                .map(String::trim).filter(role -> !role.isBlank()).collect(Collectors.toSet()))
            .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(
            JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
        return new TokenOut(token, "Bearer", expirationSeconds);
    }
}
