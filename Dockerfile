FROM adoptopenjdk/openjdk16

RUN mkdir /opt/app

COPY target/gateway.jar /opt/app/app.jar
COPY target/gateway.properties /opt/app/server.properties
EXPOSE 4567

CMD ["java", "-jar", "/opt/app/app.jar", "/opt/app/server.properties"]