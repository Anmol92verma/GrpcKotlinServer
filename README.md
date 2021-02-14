## Instruction to build and run in docker

1. Create env.list file in the root of project
2. Add the database name and mongodb connection string to this file

database=whatsappdb
mongodburi=mongodb+srv://
certChainFilePath=/root/my-public-key-cert.pem
privateKeyFilePath=/root/my-private-key.pem

3. Build the code using ./gradlew build
4. build with 
> docker build --build-arg JAR_FILE=build/libs/whatsappclone-0.0.1-SNAPSHOT.jar -t DOCKER_USERNAME/whatsappclone .
5. run with 
>docker ./gradlew build && docker run --env-file env.list -p 8080:8080 DOCKER_USERNAME/whatsappclone
6. GOOD READ -> https://github.com/grpc/grpc-java/blob/master/SECURITY.md#tls-with-netty-tcnative-on-boringssl 

## Setup TransportSecurity For localhost
$ mkdir certs # in your project root dir
$ openssl req -x509 -newkey rsa:4096 -keyout certs/my-private-key.pem -out certs/my-public-key-cert.pem -days 365 -nodes -subj '/CN=localhost'

For JAVA 8 and below check https://github.com/grpc/grpc-java/blob/master/SECURITY.md#tls-with-netty-tcnative-on-boringssl

This sample uses JAVA9 docker image :)

## Instructions for OS level run

ENV Variables required

database=whatsappdb;
mongodburi=mongodb://127.0.0.1:27017/?gssapiServiceName=mongodb;
certChainFilePath=/Users/anmolverma/IdeaProjects/GrpcKotlinServer/certs/my-public-key-cert.pem;
privateKeyFilePath=/Users/anmolverma/IdeaProjects/GrpcKotlinServer/certs/my-private-key.pem;
twilio_sid=AC7d04ef4cbfc7bfbb19c; UPDATE FOR YOUR ACC
twilio_auth=09159a4ea8ee71a37983; UPDATE FOR YOUR ACC
twilio_ver_service=VA4cdbf7b85bb7e64894e9e UPDATE FOR YOUR ACC