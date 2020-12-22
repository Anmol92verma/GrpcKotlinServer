#!/bin/sh

./gradlew build
docker build --build-arg JAR_FILE=build/libs/whatsappclone-0.0.1-SNAPSHOT.jar -t anmolverma/whatsappclone .
docker run --env-file env.list -p 8443:8443 anmolverma/whatsappclone