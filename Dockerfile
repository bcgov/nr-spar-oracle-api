FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="Ricardo Montania Prado de Campos <ricardo.campos@encora.com>"

RUN mkdir -p /usr/share/service/
WORKDIR /usr/share/service/

RUN mkdir config
RUN mkdir dump
RUN mkdir public
RUN mkdir artifacts

ENV LANG en_CA.UTF-8
ENV LANGUAGE en_CA.UTF-8
ENV LC_ALL en_CA.UTF-8

COPY InstallCert.java .
COPY HealthCheck.java .

COPY target/backend-start-api.jar artifacts/backend-start-api.jar
COPY dockerfile-entrypoint.sh dockerfile-entrypoint.sh
RUN chmod -R g+w . && \
    chmod g+x dockerfile-entrypoint.sh && \
    chmod g+w ${JAVA_HOME}/lib/security/cacerts

EXPOSE 8090

ENTRYPOINT ["/usr/share/service/dockerfile-entrypoint.sh"]
