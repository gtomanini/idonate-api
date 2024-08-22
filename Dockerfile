# Usar uma imagem base com JDK 21
FROM eclipse-temurin:21-jdk

# Instalar o Tomcat
ENV CATALINA_HOME /opt/tomcat
RUN mkdir -p "$CATALINA_HOME"
WORKDIR $CATALINA_HOME
RUN curl -O https://downloads.apache.org/tomcat/tomcat-9/v9.0.78/bin/apache-tomcat-9.0.78.tar.gz \
    && tar xvf apache-tomcat-9.0.78.tar.gz --strip-components=1 \
    && rm apache-tomcat-9.0.78.tar.gz \
    && rm -rf webapps/*

# Copiar o WAR para o diretório webapps do Tomcat
COPY target/nome-do-app.war $CATALINA_HOME/webapps/

# Expor a porta 8080
EXPOSE 8080

# Comando para iniciar o Tomcat
CMD ["bin/catalina.sh", "run"]
