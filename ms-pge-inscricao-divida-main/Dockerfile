FROM maven:3.9.2-amazoncorretto-17-debian-bullseye as builder

RUN mkdir -p /opt/google

RUN echo "${BUCKET_GOOGLE_CLOUD_CREDENTIALS}" > /opt/google/credentials.json

WORKDIR /usr/src/mymaven

COPY . .

RUN mvn clean -U install --file pom.xml

FROM openjdk:21-ea-17-jdk-slim-buster

ENV LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib:/data/lib

COPY --from=builder /usr/src/mymaven/target/ms-pge-inscricao-divida-*.jar /ms-pge-inscricao-divida.jar

CMD ["java", "-Duser.timezone=GMT-03", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-pge-inscricao-divida.jar"]
