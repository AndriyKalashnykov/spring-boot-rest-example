#!/bin/bash

docker rm -f spring-boot-rest-example
docker build  -f Dockerfile.maven-multi-stage-layer-cached -t spring-boot-rest-example .
