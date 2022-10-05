#!/bin/sh

DEBUG_PRINT=`$DEBUG_MODE -eq true && echo $JAVA_DEBUG_OPS || echo ""`

java --source 17 InstallCert.java --quiet "${DATABASE_HOST}:${DATABASE_PORT}"
keytool -exportcert -alias "${DATABASE_HOST}-1" -keystore jssecacerts -storepass changeit -file oracle.cer
keytool -importcert -alias orakey -noprompt -cacerts -storepass changeit -file oracle.cer

java \
    -Djava.security.egd=file:/dev/./urandom \
    $JAVA_OPS \
    $DEBUG_PRINT \
    -jar \
    /usr/share/service/service.jar
