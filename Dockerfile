FROM eclipse-temurin:21.0.5_11-jre-ubi9-minimal

ARG JAR="app/target/app-1.0-SNAPSHOT.jar"

COPY $JAR /tariffCalculator.jar

ENTRYPOINT ["java","-jar"]
CMD ["tariffCalculator.jar"]