FROM maven:3.6-jdk-8-alpine as MAVEN_BUILDER

ENV KEY_PASSWORD changeme

COPY ./ /tmp/
WORKDIR /tmp/

RUN mvn dependency:go-offline

RUN mvn package

RUN \
    keytool -genkey -alias holidayschecker -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -storepass \
    $KEY_PASSWORD -validity 3650 -dname "CN=bluestone.patrykmilewski.com, OU=Main, O=PatrykMilewski, L=Gdansk, S=Pomorskie, C=PL"

FROM openjdk:8-jre-alpine

# copy PKCS12 keys, that will be used for HTTPS from builder
RUN mkdir -p /opt/patrykmilewski/keys
COPY --from=MAVEN_BUILDER /tmp/keystore.p12 /opt/patrykmilewski/keys

# copy artifacts from builder
RUN mkdir -p /usr/share/holidayschecker
COPY --from=MAVEN_BUILDER /tmp/target/bluestone*.jar /usr/share/holdayschecker/bluestone.jar

ENTRYPOINT java -jar /usr/share/holidayschecker/bluestone.jar -Dspring.profiles.active=production