FROM openjdk:11
EXPOSE 8080
ADD build/libs/users-api-0.0.1-SNAPSHOT.jar /app/users-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/users-api-0.0.1-SNAPSHOT.jar"]