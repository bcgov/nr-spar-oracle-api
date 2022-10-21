#!/bin/sh

java --source 17 InstallCert.java --quiet "${DATABASE_HOST}:${DATABASE_PORT}"
keytool -exportcert -alias "${DATABASE_HOST}-1" -keystore jssecacerts -storepass changeit -file oracle.cer
keytool -importcert -alias orakey -noprompt -cacerts -storepass changeit -file oracle.cer

./mvnw spring-boot:run

