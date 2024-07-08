FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY target/guidein-backend-image.jar guidein-backend-image.jar
ENTRYPOINT [ "java","-jar","guidein-backend-image.jar" ]