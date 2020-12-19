package com.mutualmobile.whatsappclone.models

import com.mutualmobile.whatsappclone.models.User.Companion.PHONE
import com.mutualmobile.whatsappclone.models.User.Companion.UID
import com.mutualmobile.whatsappclone.models.User.Companion.VERIFIED
import org.bson.Document

data class User(val userId: String,
                val phone: String,
                val isVerified: Boolean) {
  companion object {
    const val UID = "uid"
    const val PHONE = "PHONE"
    const val VERIFIED = "VERIFIED"
  }
}

fun Document.toAppUser(): User {
  return User(userId = this.getString(UID),
      phone = this.getString(PHONE),
      isVerified = this.getBoolean(VERIFIED))
}

fun User.toDocument(): Document {
  return Document(UID, this.userId)
      .append(VERIFIED, this.isVerified)
      .append(PHONE, this.phone)
}