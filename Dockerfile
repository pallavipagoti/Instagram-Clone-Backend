FROM maven:3.9.9 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:22

WORKDIR /app

COPY --from=build /app/target/instagram-clone.jar .
#ADD target/instagram-clone.jar instagram-clone.jar

EXPOSE 8080

#COPY .env .env
ENTRYPOINT ["java","-jar","/app/instagram-clone.jar"]

