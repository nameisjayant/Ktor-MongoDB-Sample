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

//
//    suspend fun adduser(username: String, email: String, password: String): Users? {
//        userCollection.insertOne(Users(userId = null, email = email, username = username, password = password))
//        return userCollection.findOne(Users::email eq email)
//    }

    suspend fun addUser(users: Users):Users{
        userCollection.insertOne(users)
        return users
    }

}