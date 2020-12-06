package com.mutualmobile.whatsappclone.twilio

import org.springframework.data.mongodb.repository.MongoRepository

interface SMSRepository : MongoRepository<SMS, String>