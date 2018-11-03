FROM openjdk:8-jre-alpine
MAINTAINER Camilo Niño <camilo.nino@c3m.com.co>
WORKDIR /app
COPY target/consulta-generos-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "consulta-generos-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
