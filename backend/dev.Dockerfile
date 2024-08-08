FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.5-bin.zip && \
    unzip gradle-7.5-bin.zip -d /opt/ && \
    ln -s /opt/gradle-7.5/bin/gradle /usr/bin/gradle && \
    gradle build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
