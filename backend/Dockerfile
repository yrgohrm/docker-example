FROM adoptopenjdk:11-jre-hotspot

RUN mkdir -p /opt/highscore
COPY runtime/winstone-0.9.10.jar /opt/highscore
COPY target/docker-example-backend.war /opt/highscore

EXPOSE 8080/tcp

ENV JDBC_SERVER=localhost:1433
ENV JDBC_DB=TheGame
ENV JDBC_USER=sa
ENV JDBC_PASSWORD=

ENTRYPOINT ["java", "-jar", "/opt/highscore/winstone-0.9.10.jar", "--warfile", "/opt/highscore/docker-example-backend.war"]
