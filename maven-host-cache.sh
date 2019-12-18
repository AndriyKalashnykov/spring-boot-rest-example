#!/bin/bash

docker run -v ~/.m2:/root/.m2 -v "$PWD":/usr/src -w /usr/src maven:3-jdk-8 mvn clean package
docker rm -f spring-boot-rest-example
docker build  -f Dockerfile.maven-host-cache -t spring-boot-rest-example .
docker run -m500M --name spring-boot-rest-example -p 8080:8080 -p 8181:8081 -p 8778:8778 spring-boot-rest-example:latest