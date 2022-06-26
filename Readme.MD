# Spring Boot Kafka

## Run application

* Use docker to deploy kafka with registry
* Run gradle build to generate avro source files
* Run application
* Use following request to send data to kafka topic

```shell

curl --location --request POST 'localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Ashutosh",
    "id": 1001,
    "age":32,
    "city":"London"
}'

```