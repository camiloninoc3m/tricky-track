FROM openjdk:8-jre-alpine
MAINTAINER Camilo Niño <camilo.nino@c3m.com.co>
WORKDIR /app
COPY target/consulta-establecimientos-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "consulta-establecimientos-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
