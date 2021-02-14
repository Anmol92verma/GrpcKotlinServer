package com.mutualmobile.whatsappclone.posts

import kotlinx.coroutines.Dispatchers

class PostSubmitServiceImpl : PostServiceGrpcKt.PostServiceCoroutineImplBase(Dispatchers.IO) {
    override suspend fun submitPost(request: PostRequest): PostResponse {
        return super.submitPost(request)
    }
}