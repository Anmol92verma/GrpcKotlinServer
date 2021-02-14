package com.mutualmobile.whatsappclone.di

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mutualmobile.whatsappclone.di.qualifiers.UsersMongoCollection
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMongoClient(): MongoClient {
        val connectionString = System.getenv("mongodburi")
        return MongoClients.create(connectionString)
    }

    @Provides
    @Singleton
    fun provideMongoDatabase(mongoClient: MongoClient): MongoDatabase {
        val databaseName = System.getenv("database")
        return mongoClient.getDatabase(databaseName)
    }

    @Provides
    @Singleton
    @UsersMongoCollection
    fun provideUsersCollection(mongoDatabase: MongoDatabase) = mongoDatabase.getCollection("users")


}
