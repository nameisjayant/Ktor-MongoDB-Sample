package com.example.repository

import com.example.data.Users
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseFactory {

    private val client = KMongo.createClient().coroutine
    private val database = client.getDatabase("user")
    val userCollection: CoroutineCollection<Users> = database.getCollection()


    suspend fun addUser(users: Users): Users {
        userCollection.insertOne(users)
        return users
    }

    suspend fun getAllUsers():List<Users> = userCollection.find().toList()
    suspend fun getUserById(userId:String):List<Users> = userCollection.find(Users::userId eq userId).toList()
    suspend fun deleteUserById(userId: String):Boolean = userCollection.deleteOne(Users::userId eq userId).wasAcknowledged()

}