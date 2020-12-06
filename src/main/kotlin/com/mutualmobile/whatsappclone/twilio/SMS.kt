package com.mutualmobile.whatsappclone.twilio

import org.springframework.data.annotation.Id

data class SMS(@Id val id: String? = null, val forUser: String, val time: Long)