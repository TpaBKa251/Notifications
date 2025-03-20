FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
COPY service-for-dormitory-firebase-adminsdk-fbsvc-7745a3b8b1.json /app/service-for-dormitory-firebase-adminsdk-fbsvc-7745a3b8b1.json
RUN chmod +x gradlew && ./gradlew assemble
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/build/libs/Notifications-0.0.1-SNAPSHOT.jar"]
