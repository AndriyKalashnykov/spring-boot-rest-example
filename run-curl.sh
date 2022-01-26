#!/bin/bash

# set -e
# set -x

curl -X POST 'http://localhost:8080/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' --data @hotel.json --stderr -
# curl -X POST 'http://localhost:8080/v1/hotels' --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"name":"Beds R Us","description":"Very basic, small rooms but clean","city":"Santa Ana","rating":2}' --stderr -
# http POST 'http://localhost:8080/v1/hotels' < hotel.json

curl -X GET --silent 'http://localhost:8080/v1/hotels?page=0&size=10' --stderr - 2>&1 | jq .
# http  'http://localhost:8080/v1/hotels?page=0&size=10'

curl -X GET --silent 'http://localhost:8181/health' --stderr - 2>&1 | jq .
