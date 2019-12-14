#!/bin/bash

# set -e
# set -x

docker stop spring-boot-rest-example

# adding 100 to port number to avoid local conflicts (McAfee runs on 8081)
docker run -d --rm --name spring-boot-rest-example -p 8080:8080 -p 8181:8081 spring-boot-rest-example:latest &
sleep 15

exec ./run-curl.sh
