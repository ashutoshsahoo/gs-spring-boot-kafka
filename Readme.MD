# Spring Boot Kafka

## Run application

* Use docker compose to deploy kafka with registry and control-center
* Use control-center url [http://localhost:9021](http://localhost:9021) to open dashboard
* Run gradle build to generate avro source files
* Run application
* Use following request to send data to kafka topic

```shell

curl --location --request GET 'http://127.0.0.1:8080/api/v1/users' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Ashutosh",
    "id": 1001,
    "age":32,
    "city":"London"
}'

```