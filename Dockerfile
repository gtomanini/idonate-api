# Usando uma imagem base do JDK
FROM openjdk:21-jdk-slim

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar o arquivo JAR gerado para o contêiner
COPY target/idonate-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta utilizada pela aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
