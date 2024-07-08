FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/guidein-backend-image.jar guidein-backend-image.jar
ENTRYPOINT [ "java","-jar","/guidein-backend-image.jar" ]