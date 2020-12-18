package com.mutualmobile.whatsappclone.models

import com.mutualmobile.whatsappclone.models.User.Companion.FULLNAME
import com.mutualmobile.whatsappclone.models.User.Companion.UID
import com.mutualmobile.whatsappclone.models.User.Companion.USERNAME
import org.bson.Document

data class User(val userId: String, val userName: String, val fullName: String) {
  companion object {
    const val UID = "uid"
    const val USERNAME = "userName"
    const val FULLNAME = "fullName"
  }
}

fun User.toDocument(): Document {
  return Document(UID, this.userId)
      .append(USERNAME, this.userName)
      .append(FULLNAME, this.fullName)
}