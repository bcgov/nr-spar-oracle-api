FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="Ricardo Montania Prado de Campos <ricardo.campos@encora.com>"

WORKDIR /usr/share/service/
RUN mkdir -p /usr/share/service/config
RUN mkdir -p /usr/share/service/dump
RUN mkdir -p /usr/share/service/public

ENV LANG en_CA.UTF-8
ENV LANGUAGE en_CA.UTF-8
ENV LC_ALL en_CA.UTF-8

COPY InstallCert.java .

COPY ./target/backend-start-api.jar /usr/share/service/service.jar
COPY dockerfile-entrypoint.sh /usr/share/service/dockerfile-entrypoint.sh
RUN chmod -R g+w . && \
    chmod g+x dockerfile-entrypoint.sh && \
    chmod g+w ${JAVA_HOME}/lib/security/cacerts

EXPOSE 8090

ENTRYPOINT ["/usr/share/service/dockerfile-entrypoint.sh"]
