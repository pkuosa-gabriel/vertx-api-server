FROM openjdk:12-alpine

WORKDIR /home/zukdoor
COPY target/shambles-1.0.0-SNAPSHOT-fat.jar ./shambles-1.0.0-SNAPSHOT-fat.jar
ENTRYPOINT ["java", "-jar", "shambles-1.0.0-SNAPSHOT-fat.jar"]