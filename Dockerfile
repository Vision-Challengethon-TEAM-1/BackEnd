FROM openjdk:17
ARG JAR_FILE=build/libs/cheollian-*-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-Duser.timezone=GMT+9", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
