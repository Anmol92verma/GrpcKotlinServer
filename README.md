## Instruction to build and run in docker

1. Create env.list file in the root of project
2. Add the database name and mongodb connection string to this file
>database=whatsappdb

>mongodburi=mongodb+srv://
3. Build the code using ./gradlew build
4. build with 
> docker build --build-arg JAR_FILE=build/libs/whatsappclone-0.0.1-SNAPSHOT.jar -t DOCKER_USERNAME/whatsappclone .
5. run with 
>docker ./gradlew build && docker run --env-file env.list -p 8080:8080 DOCKER_USERNAME/whatsappclone
6. GOOD READ -> https://github.com/grpc/grpc-java/blob/master/SECURITY.md#tls-with-netty-tcnative-on-boringssl 

## Setup TransportSecurity For localhost

$ openssl req -x509 -newkey rsa:4096 -keyout src/main/resources/my-private-key.pem -out src/main/resources/my-public-key-cert.pem -days 365 -nodes -subj '/CN=localhost'

7. add the certificate and private key to certs dir in the project folder

For JAVA 8 and below check https://github.com/grpc/grpc-java/blob/master/SECURITY.md#tls-with-netty-tcnative-on-boringssl

This sample uses JAVA9 docker image :)