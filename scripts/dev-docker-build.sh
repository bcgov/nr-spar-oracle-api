#!/usr/bin/env sh

PATH_TO_SCRIPT=$(dirname "$0")

if [ -f "$PATH_TO_SCRIPT"/dev.env ]; then
    export $(grep -v '^#' "$PATH_TO_SCRIPT"/dev.env | xargs)
fi

: "${IMAGE_TAG:=bcgov/nrbestapi-test-service-api}"

docker build -t "$IMAGE_TAG" "$PATH_TO_SCRIPT"/..
