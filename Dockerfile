FROM openjdk:17-slim
COPY /build/libs/MusicBot-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080
