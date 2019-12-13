#!/bin/bash

docker run -v ~/.m2:/root/.m2 -v "$PWD":/usr/src -w /usr/src maven:3-jdk-8 mvn clean package
docker rm -f spring-boot-rest-example
docker build  -f Dockerfile.maven-host-cache -t spring-boot-rest-example .
