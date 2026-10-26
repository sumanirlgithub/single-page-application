FROM openjdk:21-jdk

LABEL description="ORDER-SERVICE"

WORKDIR /app

COPY target/order-service-1.0-SNAPSHOT.jar app.jar

#RUN chmod G+RWX -R .
ENTRYPOINT ["java","-jar","/app.jar"]