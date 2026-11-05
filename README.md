# Auth

Spring Boot authentication service. `POST /auth/login` validates credentials
through Account and issues short-lived HMAC-signed JWT access tokens.

## Run locally

```bash
./mvnw test
./mvnw verify
./mvnw spring-boot:run
```

Set `ACCOUNT_URL` to the Account service URL. `JWT_SECRET` must be identical
in Auth, Gateway, Account, and Order service. `INTERNAL_SERVICE_SECRET` must
match Account.

## GitHub Actions secrets

Add these under **Settings → Secrets and variables → Actions** in
`Microsserv52/auth`:

- `ACCOUNT_URL`: Account service base URL, such as
  `https://account.example.internal`.
- `JWT_SECRET`: shared JWT HMAC secret.
- `INTERNAL_SERVICE_SECRET`: shared internal service credential.
- `ACCESS_TOKEN_EXPIRATION_SECONDS`: token lifetime, normally `900`.
