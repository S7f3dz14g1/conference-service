FROM openjdk:17
COPY target/conference-service-0.0.1-SNAPSHOT.jar conference-service.jar
ENTRYPOINT  ["java","-jar","/conference-service.jar"]