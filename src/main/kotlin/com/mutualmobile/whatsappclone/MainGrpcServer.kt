package com.mutualmobile.whatsappclone

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mutualmobile.whatsappclone.signup.AuthServiceImpl
import io.grpc.ServerBuilder
import org.bson.Document

object MainGrpcServer {
  @JvmStatic
  fun main(args: Array<String>) {
     val databaseName = System.getenv("database")
     val connectionString = System.getenv("mongodburi")
     val mongoClient = MongoClients.create(connectionString)
     val mongoDatabase = mongoClient.getDatabase(databaseName)
     val mongoCollection: MongoCollection<Document> = mongoDatabase.getCollection("users")
    ServerBuilder
        .forPort(8080)
        .addService(AuthServiceImpl(mongoCollection)).build().apply {
          start()
          awaitTermination()
        }
  }
}
