package com.mutualmobile.whatsappclone

import com.mutualmobile.whatsappclone.di.DaggerAppComponent
import com.mutualmobile.whatsappclone.files.FileManagerServiceImpl
import com.mutualmobile.whatsappclone.posts.PostSubmitServiceImpl
import com.mutualmobile.whatsappclone.signup.AuthServiceImpl
import io.grpc.ServerBuilder

object MainGrpcServer {

  private val appComponent by lazy { DaggerAppComponent.builder().build() }

  @JvmStatic
  fun main(args: Array<String>) {
    //val certChainFilePath = System.getenv("certChainFilePath")
    //val privateKeyFilePath = System.getenv("privateKeyFilePath")

    ServerBuilder
      .forPort(8443)
      //.useTransportSecurity(File(certChainFilePath), File(privateKeyFilePath))
      .addService(AuthServiceImpl())
      .addService(PostSubmitServiceImpl())
      .addService(FileManagerServiceImpl())
      .build().apply {
        start()
        awaitTermination()
        println("running on ${this.port}")
      }
  }

  fun getGRPCAppComponent() = appComponent
}
