FROM maven:3.8.6-openjdk-18-slim AS build

ARG KEYCLOAK_SERVER_REALM

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn --no-transfer-progress -f /home/app/pom.xml clean package

FROM docker.io/openjdk:17-alpine
LABEL maintainer="Ricardo Montania Prado de Campos <ricardo.campos@encora.com>"

WORKDIR /usr/share/service/
RUN mkdir -p /usr/share/service/config
RUN mkdir -p /usr/share/service/dump
RUN mkdir -p /usr/share/service/public

ENV LANG en_CA.UTF-8
ENV LANGUAGE en_CA.UTF-8
ENV LC_ALL en_CA.UTF-8

ENV HEAP_LOG_PATH /usr/share/service/dump
ENV JAVA_OPS -Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$HEAP_LOG_PATH
ENV JAVA_DEBUG_OPS -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$HEAP_LOG_PATH/garbage-collection.log
ENV DEBUG_MODE false
ENV KEYCLOAK_SERVER_REALM=$KEYCLOAK_SERVER_REALM

COPY InstallCert.java .

COPY --from=build /home/app/target/*.jar /usr/share/service/service.jar
COPY dockerfile-entrypoint.sh /usr/share/service/dockerfile-entrypoint.sh
RUN chmod -R g+w . && \
    chmod g+x dockerfile-entrypoint.sh && \
    chmod g+w ${JAVA_HOME}/lib/security/cacerts

EXPOSE 8090

ENTRYPOINT ["/usr/share/service/dockerfile-entrypoint.sh"]
