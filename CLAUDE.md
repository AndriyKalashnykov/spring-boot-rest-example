# CLAUDE.md

## Project Overview

Spring Boot REST microservice example demonstrating CRUD operations with H2 in-memory database, Swagger2 API documentation, Spring Data JPA/Hibernate, and Spring Boot Actuator endpoints.

- **Language**: Java 11
- **Build**: Maven (Spring Boot 2.5.11 parent)
- **Packaging**: Single JAR with embedded Tomcat
- **Database**: H2 in-memory (JPA/Hibernate via Spring Data)
- **API Docs**: Swagger2 (springfox 3.0.0)
- **Monitoring**: Spring Boot Actuator + Micrometer Prometheus

## Build / Test / Run

```bash
# Build and run tests
mvn clean package

# Run tests only
mvn -B test --file pom.xml

# Run the service (default profile)
mvn clean spring-boot:run -Dspring-boot.run.profiles=default

# Run with remote debug
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

## Project Structure

```
src/main/java/com/test/example/
  SpringBootRestExampleApplication.java   # Entry point
  RestControllerAspect.java               # AOP aspect
  api/rest/
    AbstractRestHandler.java              # Base REST handler with exception mapping
    HotelController.java                  # CRUD REST controller
    docs/SwaggerConfig.java               # Swagger configuration
  domain/
    Hotel.java                            # JPA entity
    RestErrorInfo.java                    # Error response DTO
  dao/jpa/
    HotelRepository.java                  # Spring Data repository
  service/
    HotelService.java                     # Business logic
    HotelServiceEvent.java                # Custom application event
    HotelServiceHealth.java               # Custom health indicator
    ServiceProperties.java                # Configuration properties
  exception/
    DataFormatException.java              # 400 Bad Request
    ResourceNotFoundException.java        # 404 Not Found
src/test/java/com/test/example/test/
  HotelControllerTest.java               # MockMVC integration tests
```

## Key Endpoints

- `POST /v1/hotels` -- Create hotel
- `GET /v1/hotels?page=0&size=10` -- List hotels (paginated)
- `GET /v1/hotels/{id}` -- Get hotel by ID
- `PUT /v1/hotels/{id}` -- Update hotel
- `DELETE /v1/hotels/{id}` -- Delete hotel
- Swagger UI: `http://localhost:8080/swagger-ui/#/hotels`
- Actuator: `/actuator/health`, `/actuator/info`, `/actuator/metrics`, `/actuator/env`

## CI

- **test.yml** -- Builds and tests on push/PR to `master` (Maven, JDK 18, Ubuntu)
- **cleanup-runs.yml** -- Weekly cleanup of old workflow runs (retains 7 days, keeps minimum 5)

## Docker

Multi-stage and host-cache Dockerfiles available:
- `Dockerfile.maven-host-cache` -- Uses pre-built artifact from `$PWD/target`
- `Dockerfile.maven-multi-stage-layer-cached` -- Full multi-stage build
