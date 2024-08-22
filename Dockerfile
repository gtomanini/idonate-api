FROM eclipse-temurin:21-jdk

ENV CATALINA_HOME /opt/tomcat

RUN mkdir -p "$CATALINA_HOME"

RUN curl -O https://downloads.apache.org/tomcat/tomcat-9/v9.0.78/bin/apache-tomcat-9.0.78.tar.gz \
    && ls -l apache-tomcat-9.0.78.tar.gz \
    && tar xf apache-tomcat-9.0.78.tar.gz --strip-components=1 \
    && rm apache-tomcat-9.0.78.tar.gz \
    && rm -rf webapps/*

COPY target/idonate-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/

EXPOSE 8080

CMD ["bin/catalina.sh", "run"]
