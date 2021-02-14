package com.mutualmobile.whatsappclone.di

import com.mutualmobile.whatsappclone.signup.AuthServiceImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {
  fun inject(mainGrpcServer: AuthServiceImpl)
}