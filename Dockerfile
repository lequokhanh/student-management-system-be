FROM maven:3.9.5 AS build

COPY pom.xml .
RUN --mount=type=cache,target=/.m2,id=cache,uid=500,gid=500 \
    mvn -Dmaven.repo.local=/.m2/repository dependency:go-offline

COPY src/ src/
RUN --mount=type=cache,target=/.m2,id=cache,uid=500,gid=500 \
    mvn -Dmaven.repo.local=/.m2/repository package

FROM eclipse-temurin:17-jre AS final
COPY --from=build /target/*.jar /deployment/application.jar
CMD ["java", "-jar", "/deployment/application.jar"]
