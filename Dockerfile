FROM openjdk:17
EXPOSE 9090
ADD target/cloud_api-0.0.1-SNAPSHOT.jar cloud_api.jar
ENTRYPOINT ["java", "-jar", "cloud_api.jar"]