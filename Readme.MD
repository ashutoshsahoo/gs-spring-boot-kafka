# Spring Boot Kafka

## Run application

* Use docker to deploy kafka with registry
* Run gradle build to generate avro source files
* Run application
* Use following request to send data to kafka topic

```shell

curl --location --request POST 'http://127.0.0.1:8080/api/v1/users' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "1001",
    "name": "Ashutosh"
}'

```