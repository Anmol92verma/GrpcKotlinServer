#!/bin/sh

./gradlew build
docker build --build-arg JAR_FILE=build/libs/whatsappclone-0.0.1-SNAPSHOT.jar -t anmolverma/whatsappclone .
docker run --env-file env.list -p 8080:8080 anmolverma/whatsappclone