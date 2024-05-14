FROM maven:3.9.2-amazoncorretto-17-debian-bullseye as builder

WORKDIR /usr/src/mymaven

COPY . .
#COPY maven-settings.xml /usr/share/maven/conf/settings.xml 

RUN mvn clean -U install --file pom.xml

FROM openjdk:21-ea-17-jdk-slim-buster

ENV LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib:/data/lib

COPY --from=builder /usr/src/mymaven/target/ms-pge-portal-colaborador-*.jar /ms-pge-portal-colaborador.jar

CMD ["java", "-Duser.timezone=GMT-03", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-pge-portal-colaborador.jar"]