package com.mutualmobile.whatsappclone

import com.mutualmobile.whatsappclone.signup.AuthRequest
import com.mutualmobile.whatsappclone.signup.AuthServiceGrpc
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder
import java.io.File
import javax.net.ssl.SSLException

object Client {

  @Throws(SSLException::class)
  @JvmStatic fun main(args:Array<String>){
    val channel = NettyChannelBuilder.forAddress("192.168.1.15", 8443)
        .sslContext(GrpcSslContexts
            .forClient()
            .trustManager(File("/Users/anmolverma/projects/personal/WhatsAppCloneBackend/certs/my-public-key-cert.pem")) // public key
            .build())
        .build()
    println("${channel.isShutdown} ${channel.getState(true)}")
    println("Making call")

    val blockingStub = AuthServiceGrpc.newBlockingStub(channel)
    blockingStub.authorize(AuthRequest.newBuilder().setPhoneNumber("+918284866938").build())


    channel.shutdown()
  }
}