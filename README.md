# kstemplate

`kstemplate` is a Kotlin server application built with [Ktor](https://ktor.io/), designed as a template for quickly starting new backend projects. It features modular architecture, dependency injection, database integration, metrics, and more.

## Features

- **Ktor**: Fast, asynchronous Kotlin web framework.
- **Koin**: Dependency injection for modular, testable code.
- **Exposed**: ORM for database access (supports PostgreSQL, SQLite, H2).
- **kotlinx.serialization**: JSON serialization/deserialization.
- **Content Negotiation**: Automatic content conversion based on headers.
- **Micrometer Metrics**: Prometheus-compatible metrics endpoint.
- **Resources Routing**: Type-safe routing.
- **AutoHeadResponse**: Automatic HEAD responses.
- **Sessions**: Persistent sessions via cookies or headers.
- **Authentication**: Basic, JWT, and Firebase authentication support.
- **Docker**: Ready-to-use Dockerfile for containerized deployment.
- **Spotless & Detekt**: Code formatting and static analysis.

## Getting Started

### Prerequisites

- JDK 17+ (or use the provided Dockerfile)
- Gradle (or use `./gradlew` wrapper)
- PostgreSQL (or SQLite/H2 for development)

### Build & Run

```sh
# Run tests
./gradlew test

# Build the project
./gradlew build

# Run the server
./gradlew run
```

The server will start on [http://0.0.0.0:8080](http://0.0.0.0:8080) by default.

### Docker

To build and run with Docker:

```sh
# Build Docker image
docker build -t kstemplate .

# Run container
docker run -p 8080:8080 kstemplate
```

## API Endpoints

- `GET /` — Health check, returns "Hello World!"
- `POST /users` — Create a user (JSON body: `{ "name": "...", "age": ... }`)
- `GET /users/{id}` — Get user by ID
- `PUT /users/{id}` — Update user by ID
- `DELETE /users/{id}` — Delete user by ID
- `GET /metrics-micrometer` — Prometheus metrics

## Configuration

Edit `src/main/resources/application.yaml` to set environment variables, database URLs, JWT settings, etc.

## Development

- Code style enforced by Spotless (`./gradlew spotlessApply`)
- Static analysis with Detekt (`./gradlew detekt`)
- Test coverage with Kover (`./gradlew koverHtmlReportJvm`)

## License

Designed and developed by mshdabiola (lawal abiola), licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).


