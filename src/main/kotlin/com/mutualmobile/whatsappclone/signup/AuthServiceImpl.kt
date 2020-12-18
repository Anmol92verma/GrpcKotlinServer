package com.mutualmobile.whatsappclone.signup

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mutualmobile.whatsappclone.models.User
import com.mutualmobile.whatsappclone.models.toDocument
import kotlinx.coroutines.Dispatchers
import org.bson.Document
import java.util.*

class AuthServiceImpl : AuthServiceGrpcKt.AuthServiceCoroutineImplBase(coroutineContext = Dispatchers.IO) {
  private val connectionString = System.getenv("mongodburi")
  private val mongoClient = MongoClients.create(connectionString)
  private val mongoDatabase = mongoClient.getDatabase(System.getenv("database"))
  private val mongoCollection: MongoCollection<Document> = mongoDatabase.getCollection("users")

  override suspend fun authorize(request: SignupRequest): SignupResponse {
    val existingUser = mongoCollection.find(eq(User.USERNAME, request.userName)).first()
    existingUser?.let {
      return signupResponse(201, "Account already exists!")
    } ?: kotlin.run {
      return try {
        createUser(request, mongoCollection)
        signupResponse(200, "Account created successfully!")
      } catch (ex: Exception) {
        signupResponse(201, "Account can't be created! ${ex.message}")
      }
    }
  }

  private fun signupResponse(code: Int, message: String) = SignupResponse
      .newBuilder()
      .setCode(code)
      .setMessage(message)
      .build()

  private fun createUser(request: SignupRequest, mongoCollection: MongoCollection<Document>) {
    val userDoc = User(userId = uniqueId(),
        userName = request.userName,
        fullName = request.fullName).toDocument()
    mongoCollection.insertOne(userDoc)
  }

  private fun uniqueId() =
      UUID.randomUUID().toString()
}