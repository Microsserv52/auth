package Microsserv52.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginIn(@NotBlank @Email String email, @NotBlank String password) {}
