FROM gradle:6.8.3-jdk11 as gradle
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build -x test
FROM sachinsshetty/openjdk11-alpine
COPY --from=gradle /home/gradle/source/build/libs/crudrestapi-0.0.1-SNAPSHOT.jar ./
WORKDIR ./
CMD ["java", "-jar", "./crudrestapi-0.0.1-SNAPSHOT.jar"]