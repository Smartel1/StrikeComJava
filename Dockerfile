FROM openjdk:11
COPY build/libs/strike-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
CMD java -jar app.jar
