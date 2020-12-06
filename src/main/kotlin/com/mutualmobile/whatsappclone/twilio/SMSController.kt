package com.mutualmobile.whatsappclone.twilio

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/sms/auth"])
class SMSController(private val smsRepository: SMSRepository) {

    @RequestMapping(value = ["/request/{userId}"], method = [RequestMethod.GET])
    fun sendSMS(@PathVariable userId: String?): SMS {
        return when {
            !userId.isNullOrEmpty() -> {
                val sms = SMS(forUser = userId, time = System.currentTimeMillis());
                smsRepository.save(sms)
            }
            else -> {
                throw Exception("Data Not Sent")
            }
        }
    }
}