FROM adoptopenjdk/openjdk16

RUN mkdir /opt/app

COPY target/vehicle-server.jar /opt/app/app.jar
COPY target/vehicle-server.properties /opt/app/server.properties
EXPOSE 4570
EXPOSE 3306

CMD ["java", "-jar", "/opt/app/app.jar", "/opt/app/server.properties"]