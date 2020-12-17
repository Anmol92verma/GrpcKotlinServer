package com.mutualmobile.whatsappclone

import com.mutualmobile.whatsappclone.signup.AuthServiceImpl
import io.grpc.Server
import io.grpc.ServerBuilder

object WhatsappcloneApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        val server: Server = ServerBuilder
                .forPort(8080)
                .addService(AuthServiceImpl()).build()

        server.start()
        server.awaitTermination()
    }
}
