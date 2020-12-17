package com.mutualmobile.whatsappclone.signup

class AuthServiceImpl : AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {

    override suspend fun authorize(request: SignupRequest): SignupResponse {
        request.fullName
        request.userName
        return SignupResponse.newBuilder().setCode(200).setMessage("Account created sucessfully!").build()
    }
}