#!/usr/bin/env sh

# This is a script to bring quality of life to the application's development on Docker. It's meant
# to do the following:
# 1. Run the application with the same network and DNS of the host machine, so the container can
#    connect to the THE database (you must be connected to the VPN, if you need to)
# 2. Allow stopping the container by pressing Ctrl+C
# 3. Remove the container once it's stopped
# 4. Make it so that we don't need to build a new image every time the application code changes
# 5. Optionally, read and export environment variables from a file

PATH_TO_SCRIPT=$(dirname "$(realpath "$0")")

if [ -f "$PATH_TO_SCRIPT"/dev.env ]; then
    export $(grep -v '^#' "$PATH_TO_SCRIPT"/dev.env | xargs)
fi

DNS=$(nslookup gov.bc.ca | grep -Po 'Server:\s*\K.*$')
echo "Using DNS server $DNS"

: "${DEBUG:=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005}"
: "${IMAGE_TAG:=bcgov/nrbestapi-test-service-api}"
: "${CONTAINER_NAME:=backend-starting-api}"

docker run \
    --network host \
    --rm \
    --tty \
    --interactive \
    -v "$PATH_TO_SCRIPT"/../target:/usr/share/service/target \
    --dns="$DNS" \
    -e DATABASE_HOST="$DATABASE_HOST" \
    -e DATABASE_PORT="$DATABASE_PORT" \
    -e DATABASE_USER="$DATABASE_USER" \
    -e DATABASE_PASSWORD="$DATABASE_PASSWORD" \
    -e SERVICE_NAME="$SERVICE_NAME" \
    -e KEYCLOAK_REALM_URL="$KEYCLOAK_REALM_URL" \
    -e KEYCLOAK_ENABLED="$KEYCLOAK_ENABLED" \
    -e JAVA_OPTS="$JAVA_OPTS $DEBUG" \
    --name="$IMAGE_TAG" \
    "$CONTAINER_NAME"
