FROM openjdk:17-jdk
EXPOSE 8080
ADD target/guidein-backend-image.jar guidein-backend-image.jar
ENTRYPOINT [ "java","-jar","/guidein-backend-image.jar" ]