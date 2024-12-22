FROM openjdk:22-jdk


ADD target/instagram-clone.jar instagram-clone.jar

#COPY .env .env
ENTRYPOINT ["java","-jar","/instagram-clone.jar"]

