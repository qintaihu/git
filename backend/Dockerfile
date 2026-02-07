FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENV DB_USERNAME=root
ENV DB_PASSWORD=root
ENV JWT_SECRET=your-super-secret-key-change-in-production-environment-12345

ENTRYPOINT ["java", "-jar", "app.jar"]
