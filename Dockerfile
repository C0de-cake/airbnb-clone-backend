FROM openjdk:17-jdk-alpine
VOLUME /tmp
# Copia o JAR gerado para a imagem
ARG JAR_FILE=target/airbnb-clone-backend.jar
COPY ${JAR_FILE} app.jar
# Expõe a porta configurada (conforme application.yml, porta 8080)
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
