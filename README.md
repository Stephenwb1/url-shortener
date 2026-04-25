# URL Shortener

A URL shortening and redirect service built as a deep-dive into backend engineering with Spring Boot. The goal wasn't to build something novel — it was to touch every major backend technology in a realistic, production-like context.

## Live Demo
[Swagger UI](https://url-shortener-production-aef1.up.railway.app/swagger-ui/index.html)

## Tech Stack
- **Spring Boot 3** — REST API, dependency injection, Spring Security
- **PostgreSQL** — persistent storage for users and URLs
- **Redis** — caching layer for fast redirects
- **JWT** — stateless authentication
- **Docker / Docker Compose** — local development environment
- **Railway** — cloud deployment

## Architecture
```
Client → Spring Boot API → PostgreSQL
                        ↘ Redis (cache)
```

## API
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | No | Create account |
| POST | `/api/auth/login` | No | Login, returns JWT |
| POST | `/api/urls` | Yes | Shorten a URL |
| GET | `/r/{shortCode}` | No | Redirect to original URL |

## Run Locally
```bash
docker compose up
```

## What I Learned
Building this taught me how Spring Boot actually works under the hood — how the dependency injection container manages beans, how Spring Security's filter chain intercepts requests, and how JPA maps Java objects to database tables without writing SQL. The hardest part was wrangling Spring Security: understanding why JWT validation wasn't running, tracing the filter chain, and figuring out the right configuration to make stateless auth work. It took real debugging, not just following a tutorial.
