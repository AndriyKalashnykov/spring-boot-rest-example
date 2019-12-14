# Spring Boot REST microservice

Java / Maven / Spring Boot microservice

* Full integration with the latest **Spring** Framework 1.x: inversion of control, dependency injection, etc.
* Packaging as a single jar with embedded container (tomcat 8)
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port. Inject your own health / metrics info with a few lines of code.
* RESTful service using annotation: supports both XML and JSON request / response
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate
* CRUD functionality with H2 in-memory data source using Spring *Repository* pattern
* MockMVC test framework
* Self-documented APIs: Swagger2 using annotations

## Pre-requisites

- macos x
- sdkman
- JDK
- maven 3.x
- curl
- http
- minikube
- docker

## Run

This application is packaged as a jar which has Tomcat 8 embedded.

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
  java -jar -Dspring.profiles.active=default target/spring-boot-rest-example-0.0.1.jar
```        
or
```
  mvn spring-boot:run -Drun.arguments="spring.profiles.active=default"
```


### System health, configurations, etc.

```
http://localhost:8081/env
http://localhost:8081/health
http://localhost:8081/info
http://localhost:8081/metrics
http://localhost:8081/configprops

http://localhost:8080/swagger-ui.html
```

### Micro-service API

```
curl -X POST 'http://localhost:8080/example/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json --stderr -
```
or
```
http POST 'http://localhost:8080/example/v1/hotels' < hotel.json
```
or
```
curl -X POST 'http://localhost:8080/example/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"name":"Beds R Us","description":"Very basic, small rooms but clean","city":"Santa Ana","rating":2}' --stderr -
```

### Retrieve a paginated list of hotels

```
curl --silent 'http://localhost:8080/example/v1/hotels?page=0&size=10' --stderr -  2>&1 | jq .
```
or
```
http  'http://localhost:8080/example/v1/hotels?page=0&size=10'
```
### Swagger 2 API docs

```
open -a /Applications/Google\ Chrome.app http://localhost:8080/swagger-ui.html
```

minikube delete --all
minikube start -p minikube --memory=8192 --cpus=6 --vm-driver=hyperkit
eval $(minikube docker-env)
minikube start --extra-config=apiserver.anonymous-auth=false --insecure-registry=localhost:5000

mvn clean package -Pk8s fabric8:build
mvn clean package -Pk8s fabric8:deploy
mvn clean package fabric8:deploy -Dfabric8.generator.from=fabric8/java-alpine-openjdk8-jdk
mvn clean package -Pk8s fabric8:undeploy

minikube service spring-boot-rest-example --url

curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json 'http://192.168.64.15:30660/example/v1/hotels'
http http://192.168.64.15:30660/example/v1/hotels?page=0&size=10


### Building docker image

  https://stackify.com/guide-docker-java/

  docker run -v ~/.m2:/root/.m2 -v "$PWD":/usr/src -w /usr/src maven:3-jdk-8 mvn clean package
  The compiled artifacts will be in $PWD/target

  docker build  -f Dockerfile.maven-multi-stage-layer-cached -t spring-boot-rest-example .

  sudo lsof -i :8081
  docker rm -f spring-boot-rest-example

  # adding 100 to port number to avoid local conflicts (McAfee runs on 8081)
  docker run --name spring-boot-rest-example -p 8080:8080 -p 8181:8081 spring-boot-rest-example:latest

  curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json 'http://localhost:8080/example/v1/hotels'
  http http://localhost:8080/example/v1/hotels?page=0&size=10
  http http://localhost:8080/swagger-ui.html
  http http://localhost:8181/health


### Attaching to the app from IDE

Run the service with these command line options:

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
or
```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=test -Ddebug -jar target/spring-boot-rest-example-0.0.1.jar
```

IntelliJ : Run -> Edit configuration -> Remote.

![IntelliJ IDEA](./img/idea-remote.png)
