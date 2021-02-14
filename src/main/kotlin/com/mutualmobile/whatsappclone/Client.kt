package com.mutualmobile.whatsappclone

import com.mutualmobile.whatsappclone.signup.AuthRequest
import com.mutualmobile.whatsappclone.signup.AuthServiceGrpc
import io.grpc.ManagedChannelBuilder
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder
import java.io.File
import javax.net.ssl.SSLException
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext




object Client {

  @Throws(SSLException::class)
  @JvmStatic fun main(args:Array<String>){
    val sslcontext: SslContext = GrpcSslContexts.forClient() // if server's cert doesn't chain to a standard root
      .trustManager(File("/Users/anmolverma/IdeaProjects/GrpcKotlinServer/certs/my-public-key-cert.pem")) // public key
      .keyManager(File("/Users/anmolverma/IdeaProjects/GrpcKotlinServer/certs/my-private-key.pem"),
        File("/Users/anmolverma/IdeaProjects/GrpcKotlinServer/certs/my-public-key-cert.pem")) // client cert
      .build()

    val channel = NettyChannelBuilder.forAddress("192.168.1.11", 8443)
        .sslContext(sslcontext)
        .build()
    println("${channel.isShutdown} ${channel.getState(true)}")
    println("Making call")

    val blockingStub = AuthServiceGrpc.newBlockingStub(channel)
    blockingStub.authorize(AuthRequest.newBuilder().setPhoneNumber("+918284866938").build())


    channel.shutdown()
  }
}