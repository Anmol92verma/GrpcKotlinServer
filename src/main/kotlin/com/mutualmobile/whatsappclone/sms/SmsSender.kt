package com.mutualmobile.whatsappclone.sms

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification
import com.twilio.rest.verify.v2.service.VerificationCheck

object SmsSender {
  // Find your Account Sid and Auth Token at twilio.com/console
  private val ACCOUNT_SID = System.getenv("twilio_sid")
  private val AUTH_TOKEN = System.getenv("twilio_auth")
  private val VER_SERVICE_SID = System.getenv("twilio_ver_service")

  init {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
  }

  fun verifyCode(phoneNumber: String, code: String): VerificationCheck {
    return VerificationCheck.creator(
        VER_SERVICE_SID,
        code)
        .setTo(phoneNumber).create()
  }

  fun send(phoneNumber: String): Verification {
    println("Create verification for $phoneNumber")
    val created = Verification.creator(
        VER_SERVICE_SID,
        phoneNumber,
        "sms")
        .create()
    println("Created verification for ${created.status}")
    return created
  }
}