FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY target/guidein-backend-image.jar /app/guidein-backend-image.jar
ENTRYPOINT [ "java","-jar","/app/guidein-backend-image.jar" ]