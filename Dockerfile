FROM openjdk:17-jdk-alpine
WORKDIR /guidein-backend-image
COPY target/guidein-backend-image.jar guidein-backend-image.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","/guidein-backend-image.jar" ]