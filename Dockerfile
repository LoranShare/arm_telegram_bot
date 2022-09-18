FROM eclipse-temurin
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=default_name
ENV BOT_TOKEN=666666
WORKDIR /myapp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "app.jar"]