FROM openjdk:11
COPY target/brasilprev-0.0.1-SNAPSHOT.jar brasilprev-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/brasilprev-0.0.1-SNAPSHOT.jar"]