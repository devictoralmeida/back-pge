# Instala git e clona projeto no ambiente da JDK.
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt

COPY . pge-divida-ativa-api

# Compila projeto.
WORKDIR /opt/pge-divida-ativa-api
RUN bash mvnw dependency:go-offline
RUN bash mvnw clean install -DskipTests

# Copia projeto compilado pra ambiente da JRE e executa.
FROM eclipse-temurin:17-jre-jammy
ENV TZ=America/Fortaleza
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/pge-divida-ativa-api/target/*.jar ./*.jar
ENTRYPOINT ["java", "-jar", "./*.jar" ]
