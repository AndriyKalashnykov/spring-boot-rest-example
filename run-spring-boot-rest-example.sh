#!/bin/bash

set -e
set -x

docker stop spring-boot-rest-example

# adding 100 to port number to avoid local conflicts (McAfee runs on 8081)
docker run -d --rm --name spring-boot-rest-example -p 8080:8080 -p 8181:8081 spring-boot-rest-example:latest
sleep 5


$(curl -vv -X POST http://localhost:8080/example/v1/hotels -H 'content-type: application/json' -H 'accept: application/json' -d '{"name":"Beds R Us","description":"Very basic, small rooms but clean","city":"Santa Ana","rating":2}')
# $(http POST 'http://localhost:8080/example/v1/hotels' < hotel.json)

# STATUS=$(http POST 'http://localhost:8080/example/v1/hotels' < hotel.json  2>&1 | grep HTTP/  | cut -d ' ' -f 2)
# echo $STATUS

# $(http  'http://localhost:8080/example/v1/hotels?page=0&size=10')
$(curl -s -X GET http://localhost:8080/example/v1/hotels?page=0&size=10)


# rm out.json
# $(http -hdo ./out.json 'http://localhost:8080/example/v1/hotels?page=0&size=10' 2>&1 | grep HTTP/  | cut -d ' ' -f 2)
# sleep 3
# jq . out.json
