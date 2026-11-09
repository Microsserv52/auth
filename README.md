# Auth

**Application stack**

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat)](https://dev.java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Bean Validation](https://img.shields.io/badge/Bean_Validation-6DB33F?style=flat)](https://jakarta.ee/specifications/bean-validation/)
[![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=flat)](https://projectlombok.org/)
[![Spring MVC](https://img.shields.io/badge/Spring_MVC-6DB33F?style=flat&logo=spring&logoColor=white)](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![OAuth 2.0](https://img.shields.io/badge/OAuth_2.0-3C6E71?style=flat)](https://oauth.net/2/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Spring Actuator](https://img.shields.io/badge/Spring_Actuator-6DB33F?style=flat&logo=springboot&logoColor=white)](https://docs.spring.io/spring-boot/reference/actuator/index.html)
[![OpenFeign](https://img.shields.io/badge/OpenFeign-6DB33F?style=flat&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud-openfeign)

**Build, checks and delivery**

[![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-25A162?style=flat&logo=junit5&logoColor=white)](https://junit.org/)
[![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)](https://git-scm.com/)
[![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)](https://github.com/features/actions)
[![CodeQL](https://img.shields.io/badge/CodeQL-181717?style=flat&logo=github&logoColor=white)](https://codeql.github.com/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-D24939?style=flat)](https://www.jacoco.org/jacoco/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)](https://www.docker.com/)
[![GHCR](https://img.shields.io/badge/GHCR-181717?style=flat&logo=github&logoColor=white)](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry)

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

| Variable                          | Purpose                          |
| --------------------------------- | -------------------------------- |
| `ACCOUNT_URL`                     | Account service base URL         |
| `JWT_SECRET`                      | Shared JWT HMAC secret           |
| `INTERNAL_SERVICE_SECRET`         | Account credential lookup secret |
| `ACCESS_TOKEN_EXPIRATION_SECONDS` | Access-token lifetime            |

## GitHub Actions secrets

Add these under **Settings → Secrets and variables → Actions** in
`Microsserv52/auth`:

- `ACCOUNT_URL`: Account service base URL, such as
  `https://account.example.internal`.
- `JWT_SECRET`: shared JWT HMAC secret.
- `INTERNAL_SERVICE_SECRET`: shared internal service credential.
- `ACCESS_TOKEN_EXPIRATION_SECONDS`: token lifetime, normally `900`.
