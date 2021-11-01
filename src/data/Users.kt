package com.example.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Users(
    @BsonId
    val userId: String? = ObjectId().toString(),
    val username: String? = "username",
    val email: String? = "email",
    val password: String? = "password"
)
