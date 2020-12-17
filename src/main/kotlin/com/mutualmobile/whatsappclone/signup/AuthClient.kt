package com.mutualmobile.whatsappclone.signup

import io.grpc.ManagedChannelBuilder

object AuthClient {
    @JvmStatic
    fun main(args: Array<String>) {
        val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build()

        val stub = AuthServiceGrpc.newBlockingStub(channel)

        val helloResponse = stub.authorize(SignupRequest.newBuilder()
                .setFullName("Baeldung")
                .setUserName("gRPC")
                .build())
        println("${helloResponse.code} ${helloResponse.message}")

        channel.shutdown()
    }
}