FROM openjdk:17-alpine
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} hierarchy-resolver.jar
ENTRYPOINT ["java","-jar","/hierarchy-resolver.jar"]
