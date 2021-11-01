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

                val user = db.getAllUsers()
                call.respond(user)

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]

            try {
                val user = db.getUserById(id!!)
                call.respond(user)

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]

            try {
                val delete = db.deleteUserById(id!!)
                if (delete) {
                    call.respond("user delete")
                } else {
                    call.respond("user not found")
                }

            } catch (e: Exception) {
                application.log.error("Failed to get data", e.message)
            }
        }


    }

}