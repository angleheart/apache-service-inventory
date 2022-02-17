FROM adoptopenjdk/openjdk16

RUN mkdir /opt/app

COPY target/customer-server.jar /opt/app/app.jar
COPY target/customer-server.properties /opt/app/server.properties
EXPOSE 4568
EXPOSE 3306

CMD ["java", "-jar", "/opt/app/app.jar", "/opt/app/server.properties"]