## Instruction to build and run in docker

1. Create env.list file in the root of project
2. Add the database name and mongodb connection string to this file
    * database=whatsappdb
    * mongodburi=mongodb+srv://
3. run with docker ./gradlew build && docker run --env-file env.list -p 8080:8080 DOCKER_USERNAME/whatsappclone