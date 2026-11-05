package Microsserv52.auth.api;

public record TokenOut(String accessToken, String tokenType, long expiresIn) {}
