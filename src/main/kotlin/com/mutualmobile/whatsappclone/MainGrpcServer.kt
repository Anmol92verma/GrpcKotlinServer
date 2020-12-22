package com.mutualmobile.whatsappclone

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mutualmobile.whatsappclone.signup.AuthServiceImpl
import io.grpc.ServerBuilder
import org.bson.Document
import java.io.File
object MainGrpcServer {
  @JvmStatic
  fun main(args: Array<String>) {
    val databaseName = System.getenv("database")
    val connectionString = System.getenv("mongodburi")
    val mongoClient = MongoClients.create(connectionString)
    val mongoDatabase = mongoClient.getDatabase(databaseName)
    val certChainFilePath = System.getenv("certChainFilePath")
    val privateKeyFilePath = System.getenv("privateKeyFilePath")
    val mongoCollection: MongoCollection<Document> = mongoDatabase.getCollection("users")

    ServerBuilder
        .forPort(8443)
       .useTransportSecurity(File(certChainFilePath), File(privateKeyFilePath))
        .addService(AuthServiceImpl(mongoCollection))
        .build().apply {
          start()
          awaitTermination()
          println("running on ${this.port}")
        }
  }
}
