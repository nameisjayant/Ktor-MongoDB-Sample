package com.example.routes

import com.example.data.Users
import com.example.repository.DatabaseFactory
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.request.receive
import org.litote.kmongo.eq
import java.lang.Exception


fun Route.userRoute(
    db: DatabaseFactory
) {

    route("/user") {

        post {
            val requestBody = call.receive<Users>()

            try {
                val user = db.addUser(requestBody)
                call.respond(user)

            } catch (e: Exception) {
                application.log.error("Failed to register user", e.message)
            }
        }

        get {
            try {

                val user = db.userCollection.find().toList()
                call.respond(user)

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]

            try {
                val user = db.userCollection.find(Users::userId eq id).toList()
                call.respond(user)

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]

            try {
                val delete = db.userCollection.deleteOneById(id!!).wasAcknowledged()
                if (delete) {
                    call.respond("user delete")
                } else {
                    call.respond("user not found")
                }

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }
        }

        put("/{id}") {
            val requestBody = call.receive<Users>()
            val id = call.parameters["id"]
            try {
                val updated = db.userCollection.updateOneById(id!!, requestBody).wasAcknowledged()
                if (updated) {
                    call.respond("user updated!!")
                } else {
                    call.respond("user not found")
                }

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }

        }

    }

}