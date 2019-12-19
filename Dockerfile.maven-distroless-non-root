ARG MVN_VERSION=3.6.3
ARG JDK_VERSION=11

FROM maven:${MVN_VERSION}-jdk-${JDK_VERSION}-slim as MAVEN_TOOL_CHAIN_CACHE
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY ./pom.xml /tmp/
COPY ./src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package

FROM gcr.io/distroless/java:${JDK_VERSION}

USER nonroot:nonroot

COPY --from=MAVEN_TOOL_CHAIN_CACHE --chown=nonroot:nonroot /tmp/target/spring-boot-rest-example-0.0.1.jar /spring-boot-rest-example-0.0.1.jar

EXPOSE 8080
EXPOSE 8081
EXPOSE 8778
EXPOSE 9779

ENV _JAVA_OPTIONS "-XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=90.0 \
-Djava.security.egd=file:/dev/./urandom \
-Djava.awt.headless=true -Dfile.encoding=UTF-8 \
-Dspring.output.ansi.enabled=ALWAYS \
-Dspring.profiles.active=default"

ENTRYPOINT ["java", "-jar", "/spring-boot-rest-example-0.0.1.jar"]
