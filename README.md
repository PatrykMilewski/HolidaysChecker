
# HolidaysChecker :palm_tree:

HolidayChecker is an Spring Boot application, that can be used for searching holiday's
name for two given countries starting from given date.

HolidaysChecker is using data from https://holidayapi.com/

## Example:

```bash
curl "https://localhost/holidays/check" -G --insecure \
    -d country1="US"     \
    -d country2="PL"     \
    -d date="2018-12-24"
```
Will return JSON:

```json
{
  "date": "2018-12-25",
  "name1": "Christmas Day",
  "name2": "Pierwszy dzień Bożego Narodzenia"
}
```

## API Documentation

API documentation can be accessed via Swagger:

https://localhost/

## Running up project

WARNING:
By default, project is running with "dev" Spring profile. You can change it in Dockerfile.

HolidaysChecker uses Fabric8 for Docker phases integration with Maven and Docker-compose
for better container management.

Basic building and project startup:

```bash
# cleanup
docker-compose down

# building the artifacts and Docker image
mvn clean package docker:build

# starting up application
docker-compose up -d
```

## holidayapi.com API Key

API Key can be provided by `application.properties` files, which name may vary, depends on
chosen profile.

Free version of account limits monthly requests to 100 and does not let you check for
holidays in the future.

## HTTPS

By default, Dockerfile generates PKCS12 keys used for HTTPS, but since they are self-signed,
there will be problems with authentication of domain.

It may be fixed, with providing your own PKCS12 keys, that are trusted by your computer.

## Docker multi-stage build

Given multi-stage Dockerfile, that was supposed build image inside Docker, then deploy 
artifacts to production container, is abandoned right now, due to long build times, 
that was caused by caching problems with Maven.