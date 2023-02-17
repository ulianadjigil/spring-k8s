FROM openjdk:17-alpine
EXPOSE 8082
COPY target/ipz*.jar /ipzlab3.jar
CMD ["java", "-jar", "/ipzlab3.jar"]