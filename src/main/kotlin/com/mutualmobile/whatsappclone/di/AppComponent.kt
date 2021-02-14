package com.mutualmobile.whatsappclone.di

import com.mutualmobile.whatsappclone.MainGrpcServer
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface AppComponent {
  fun inject(mainGrpcServer: MainGrpcServer)
}