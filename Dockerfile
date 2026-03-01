FROM amazoncorretto:17
WORKDIR /ravi
COPY /target/user-service.jar /ravi
CMD["java","-jar","user-service.jar"]