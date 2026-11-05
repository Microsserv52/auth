package Microsserv52.auth.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account", url = "${platform.account-url}")
public interface AccountClient {
    @GetMapping("/accounts/internal/credentials")
    Credentials credentials(@RequestParam String email,
                            @RequestHeader("X-Internal-Service") String internalServiceSecret);

    record Credentials(java.util.UUID id, String email, String passwordHash, String roles, boolean enabled) {}
}
