FROM maven:3.9.5 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre AS final
COPY --from=build /app/target/*.jar /deployment/application.jar
CMD ["java", "-jar", "/deployment/application.jar"]
