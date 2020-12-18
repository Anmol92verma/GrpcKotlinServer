package com.mutualmobile.whatsappclone

import com.mutualmobile.whatsappclone.signup.AuthServiceImpl
import io.grpc.ServerBuilder

object MainGrpcServer {
  @JvmStatic
  fun main(args: Array<String>) {
    ServerBuilder
        .forPort(8080)
        .addService(AuthServiceImpl()).build().apply {
          start()
          awaitTermination()
        }
  }
}
