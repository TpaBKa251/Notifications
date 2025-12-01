FROM eclipse-temurin:25-jdk
WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
ARG FIREBASE=service-for-dormitory-firebase-adminsdk-fbsvc-7745a3b8b1.json

COPY ${JAR_FILE} app.jar
COPY ${FIREBASE} service-for-dormitory-firebase-adminsdk-fbsvc-7745a3b8b1.json

ARG INTERNAL_REPO_LOGIN
ARG INTERNAL_REPO_PASSWORD
ENV INTERNAL_REPO_LOGIN=$INTERNAL_REPO_LOGIN
ENV INTERNAL_REPO_PASSWORD=$INTERNAL_REPO_PASSWORD

#RUN chmod +x gradlew && ./gradlew assemble
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
