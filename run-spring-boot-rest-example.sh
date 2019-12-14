#!/bin/bash

# set -e
# set -x

docker stop spring-boot-rest-example

# adding 100 to port number to avoid local conflicts (McAfee runs on 8081)
docker run -d --rm --name spring-boot-rest-example -p 8080:8080 -p 8181:8081 spring-boot-rest-example:latest &
sleep 15

# curl -X POST 'http://localhost:8080/example/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json --stderr -
# curl -X POST 'http://localhost:8080/example/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"name":"Beds R Us","description":"Very basic, small rooms but clean","city":"Santa Ana","rating":2}' --stderr -
http POST 'http://localhost:8080/example/v1/hotels' < hotel.json


http  'http://localhost:8080/example/v1/hotels?page=0&size=10'
# curl --silent 'http://localhost:8080/example/v1/hotels?page=0&size=10' --stderr -  2>&1 | jq .
