FROM gradle:6.8.3-jdk11 as gradle
COPY ./build.gradle ./build.gradle
COPY ./src ./src
RUN gradle build -x test
FROM sachinsshetty/openjdk11-alpine
WORKDIR /crudrestapi
COPY build/libs/crudrestapi-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "./crudrestapi-0.0.1-SNAPSHOT.jar"]