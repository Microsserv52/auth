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

## Environment variables

Set `ACCOUNT_URL`, `JWT_SECRET`, `INTERNAL_SERVICE_SECRET`, and
`ACCESS_TOKEN_EXPIRATION_SECONDS` in the root platform `.env` locally. In
GitHub Actions, use repository secrets; in Kubernetes, use ConfigMap values
for URLs and `platform-secrets` for credentials.

| Variable | Purpose |
| --- | --- |
| `ACCOUNT_URL` | Account service base URL |
| `JWT_SECRET` | Shared JWT HMAC secret |
| `INTERNAL_SERVICE_SECRET` | Account credential lookup secret |
| `ACCESS_TOKEN_EXPIRATION_SECONDS` | Access-token lifetime |

## GitHub Actions secrets

Add these under **Settings → Secrets and variables → Actions** in
`Microsserv52/auth`:

- `ACCOUNT_URL`: Account service base URL, such as
  `https://account.example.internal`.
- `JWT_SECRET`: shared JWT HMAC secret.
- `INTERNAL_SERVICE_SECRET`: shared internal service credential.
- `ACCESS_TOKEN_EXPIRATION_SECONDS`: token lifetime, normally `900`.
