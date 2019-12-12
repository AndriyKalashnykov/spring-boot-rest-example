# Spring Boot Microservice Example

Java / Maven / Spring Boot micro-service.

Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 8)
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port. Inject your own health / metrics info with a few lines of code.
* RESTful service using annotation: supports both XML and JSON request / response
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* MockMVC test framework
* APIs are "self-documented" by Swagger2 using annotations

## Pre-requisites

- macos x
- sdkman
- JDK
- maven 3.x
- curl
- http

## Run

This application is packaged as a war which has Tomcat 8 embedded.

* Clone this repository
```
git clone git@github.com:AndriyKalashnykov/spring-boot-rest-example.git
cd spring-boot-rest-example
```
* Select JDK
```
sdk use java 8.0.232.hs-adpt
```
or
```
sdk use java 11.0.5.hs-adpt
```
* Build the project and run the tests by running
```
mvn clean package
```
* Run the service
```
  java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.0.1.war
```        
or
```
  mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

### System health, configurations, etc.

```
http://localhost:8091/env
http://localhost:8091/health
http://localhost:8091/info
http://localhost:8091/metrics
http://localhost:8091/configprops

http://localhost:8090/swagger-ui.html
```

### Micro-service API

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json 'http://localhost:8090/example/v1/hotels'
```
or
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
"name" : "Beds R Us",
"description" : "Very basic, small rooms but clean",
"city" : "Santa Ana",
"rating" : 2
}' 'http://localhost:8090/example/v1/hotels'
```

### Retrieve a paginated list of hotels

```
curl http://localhost:8090/example/v1/hotels?page=0&size=10
```
or
```
http http://localhost:8090/example/v1/hotels?page=0&size=10
```
### Swagger 2 API docs

```
open -a /Applications/Google\ Chrome.app http://localhost:8090/swagger-ui.html
```

### Attaching to the app from IDE

Run the service with these command line options:

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
or
```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=test -Ddebug -jar target/spring-boot-rest-example-0.0.1.war
```

IntelliJ : Run -> Edit configuration -> Remote.

![IntelliJ IDEA](./img/idea-remote.png)
