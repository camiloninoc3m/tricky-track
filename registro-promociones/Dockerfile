FROM openjdk:8-jre-alpine
MAINTAINER Camilo Niño <camilo.nino@c3m.com.co>
WORKDIR /app
COPY target/registro-promociones-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "registro-promociones-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083
