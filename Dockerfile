FROM adoptopenjdk/openjdk9:latest
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY certs/my-public-key-cert.pem certs/my-private-key.pem /root/
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]