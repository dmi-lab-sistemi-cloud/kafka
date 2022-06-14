FROM openjdk:8 
WORKDIR /app
# Run mvn package -DskipTests to generage the jar
COPY target/spring-kafka-1.0-SNAPSHOT.jar /app
ENV SERVER_PORT 8080
EXPOSE $SERVER_PORT
CMD ["java", "-jar", "spring-kafka-1.0-SNAPSHOT.jar" ]
