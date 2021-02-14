package com.mutualmobile.whatsappclone.signup

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mutualmobile.whatsappclone.di.qualifiers.UsersMongoCollection
import com.mutualmobile.whatsappclone.models.User
import com.mutualmobile.whatsappclone.models.toAppUser
import com.mutualmobile.whatsappclone.models.toDocument
import com.mutualmobile.whatsappclone.sms.SmsSender
import kotlinx.coroutines.Dispatchers
import org.bson.Document
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthServiceImpl @Inject constructor() :
  AuthServiceGrpcKt.AuthServiceCoroutineImplBase(coroutineContext = Dispatchers.IO) {

  @Inject
  @UsersMongoCollection
  lateinit var mongoCollection: MongoCollection<Document>

  override suspend fun verifyOtp(request: AuthVerify): AuthResponse {
    return try {
      val verificationCheck = SmsSender.verifyCode(request.phoneNumber, request.code)
      if (verificationCheck.valid) {
        signupResponse(200, "OTP Validated!")
      } else {
        signupResponse(201, "OTP not Valid!")
      }
    } catch (ex: Exception) {
      print(ex)
      signupResponse(201, "Otp check Failed ${ex.message}")
    }
  }

  override suspend fun authorize(request: AuthRequest): AuthResponse {
    val userWPhone = mongoCollection.find(eq(User.PHONE, request.phoneNumber)).first()?.toAppUser()

    userWPhone?.let {
      return whenUserAlreadyExists(request)
    } ?: run {
      return whenUserDoesNotExist(request)
    }
  }

  private fun whenUserDoesNotExist(request: AuthRequest): AuthResponse {
    return try {
      createUser(request, mongoCollection)
      SmsSender.send(request.phoneNumber)
      signupResponse(200, "Account created successfully! Check Phone For OTP")
    } catch (ex: Exception) {
      println(ex)
      signupResponse(201, "Account can't be created! ${ex.message}")
    }
  }

  private fun whenUserAlreadyExists(request: AuthRequest): AuthResponse {
    return try {
      SmsSender.send(request.phoneNumber)
      //request for otp verification
      signupResponse(200, "Check Phone For OTP")
    } catch (ex: Exception) {
      println(ex)
      //request for otp verification
      signupResponse(201, ex.message ?: "Can't send sms otp!")
    }
  }

  private fun signupResponse(code: Int, message: String) = AuthResponse
    .newBuilder()
    .setCode(code)
    .setMessage(message)
    .build()

  private fun createUser(request: AuthRequest, mongoCollection: MongoCollection<Document>) {
    val userDoc = User(
      userId = uniqueId(),
      phone = request.phoneNumber,
      isVerified = false
    ).toDocument()
    mongoCollection.insertOne(userDoc)
  }

  private fun uniqueId() =
    UUID.randomUUID().toString()
}