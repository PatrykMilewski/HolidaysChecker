FROM openjdk:8-jre-alpine

ENV KEY_PASSWORD changeme

RUN mkdir -p /opt/patrykmilewski/keys \
 && keytool -genkey -alias holidayschecker -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -storepass \
    KEY_PASSWORD -validity 3650 -dname "CN=bluestone.patrykmilewski.com, OU=Main, O=PatrykMilewski, L=Gdansk, S=Pomorskie, C=PL"

RUN mkdir -p /opt/patrykmilewski/
ADD maven/bluestone*.jar /opt/patrykmilewski/bluestone.jar

ENTRYPOINT java -jar /opt/patrykmilewski/bluestone.jar -Dspring.profiles.active=prod