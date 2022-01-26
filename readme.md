[![test](https://github.com/AndriyKalashnykov/spring-boot-rest-example/actions/workflows/test.yml/badge.svg?branch=master)](https://github.com/AndriyKalashnykov/spring-boot-rest-example/actions/workflows/test.yml)
# Spring Boot REST microservice

Java / Maven / Spring Boot microservice

* Full integration with the latest **Spring** Framework 2.x: inversion of control, dependency injection, etc.
* Packaging as a single jar with embedded container (tomcat 8)
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port. Inject your own health / metrics info with a few lines of code.
* RESTful service using annotation: supports both XML and JSON request / response
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate
* CRUD functionality with H2 in-memory data source using Spring *Repository* pattern
* MockMVC test framework
* Self-documented APIs: Swagger2 using annotations

## Pre-requisites

* [sdkman](https://sdkman.io/install)
* [Apache Maven](https://maven.apache.org/install.html)
* [curl](https://help.ubidots.com/en/articles/2165289-learn-how-to-install-run-curl-on-windows-macosx-linux)
* [jq](https://github.com/stedolan/jq/wiki/Installation)
* [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [http](https://httpie.io/cli)
* [minikube](https://minikube.sigs.k8s.io/docs/start/)
* [Docker](https://docs.docker.com/engine/install/)

## Build and Run

This application is packaged as a jar which has Tomcat 8 embedded.

* Clone this repository

```bash
git clone git@github.com:AndriyKalashnykov/spring-boot-rest-example.git
cd spring-boot-rest-example
```
* Select JDK

```bash
sdk install java 11.0.11.hs-adpt
sdk use java 11.0.11.hs-adpt
```
* Build the project and run the tests by running

```bash
mvn clean package
```
* Run the service

```
  mvn clean spring-boot:run -Dspring-boot.run.profiles=default
```

### Swagger UI documentation links, application health, configurations 

```
http://localhost:8080/swagger-ui.html#/hotels

http://localhost:8080/actuator/env
http://localhost:8080/actuator/health
http://localhost:8080/actuator/info
http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/configprops

```

### Microservice API

```
curl -X POST 'http://localhost:8080/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json --stderr -
```
or
```
http POST 'http://localhost:8080/v1/hotels' < hotel.json
```
or
```
curl -X POST 'http://localhost:8080/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"name":"Beds R Us","description":"Very basic, small rooms but clean","city":"Santa Ana","rating":2}' --stderr -
```

### Retrieve a paginated list of hotels

```
curl -X GET --silent 'http://localhost:8080/v1/hotels?page=0&size=10' --stderr -  2>&1 | jq .
```
or
```
http  'http://localhost:8080/v1/hotels?page=0&size=10'
```
### Swagger 2 API docs

```
xdg-open  http://localhost:8080/swagger-ui.html
```


### Building docker image


#### Optional, local test only: Using local maven cache

  In order to build image quickly by compiling maven project using host OS  maven repo

  Build project, artifact will be placed in $PWD/target

  ```
  cd spring-boot-rest-example
  docker run -v ~/.m2:/root/.m2 -v "$PWD":/usr/src -w /usr/src maven:3-jdk-8 mvn clean package
  ```

  #### Build non multi-stage image using existing artifact in $PWD/target

  ```
  cd spring-boot-rest-example
  docker rm -f spring-boot-rest-example
  docker build  -f Dockerfile.maven-host-cache -t spring-boot-rest-example .
  ```

  #### Build  multi-stage image  

  ```
  docker rm -f spring-boot-rest-example
  docker build  -f Dockerfile.maven-multi-stage-layer-cached -t spring-boot-rest-example .
  ```

  #### Test application

  ```
  docker run --name spring-boot-rest-example -p 8080:8080 spring-boot-rest-example:latest

  curl -X POST 'http://localhost:8080/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json --stderr -

  curl -X GET --silent 'http://localhost:8080/v1/hotels?page=0&size=10' --stderr -  2>&1 | jq .

  ```

### Attaching to the application from IDE

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

### Deploy application to k8s

```
minikube delete --all
minikube start -p minikube --memory=16384 --cpus=6 --disk-size=30g --vm-driver=virtualbox
eval $(minikube docker-env)
eval "$(docker-machine env -u)"
# minikube start --vm-driver=virtualbox --extra-config=apiserver.anonymous-auth=false --insecure-registry=localhost:5000

# deploy to k8s

minikube ssh 'docker logs $(docker ps -a -f name=k8s_kube-api --format={{.ID}})'
```

### Test deployed application

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json $(minikube service spring-boot-rest-example --url | sed -n 1p)/v1/hotels
http $(minikube service spring-boot-rest-example --url | sed -n 1p)/v1/hotels?page=0&size=10

http $(minikube service spring-boot-rest-example --url | sed -n 2p)/swagger-ui.html
http $(minikube service spring-boot-rest-example --url | sed -n 2p)/info
http $(minikube service spring-boot-rest-example --url | sed -n 2p)/health
```

### Monitor k8s resources
```
kubectl get nodes --no-headers | awk '{print $1}' | xargs -I {} sh -c 'echo {}; kubectl describe node {} | grep Allocated -A 5 | grep -ve Event -ve Allocated -ve percent -ve -- ; echo'
kubectl top pod --all-namespaces
```
