#!/bin/sh

DEBUG_PRINT=`$DEBUG_MODE -eq true && echo $JAVA_DEBUG_OPS || echo ""`

java \
    -Djava.security.egd=file:/dev/./urandom \
    $JAVA_OPS \
    $DEBUG_PRINT \
    -jar \
    /usr/share/service/service.jar

