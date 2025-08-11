package com.mshdabiola.route

import com.mshdabiola.database.service.UserService
import com.mshdabiola.model.ExposedUser
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.resources.Resources
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.update
import org.koin.ktor.ext.getKoin
import org.koin.ktor.ext.inject
import kotlin.text.toInt

fun Application.userRoute() {

    val userService by inject<UserService>()
    routing {

        // Create user
        post("/users") {

            val user = call.receive<ExposedUser>()//
            println(user)
            val id = userService.create(user)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read user
        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            println("id: $id")
            val user = userService.read(id)
            println(user)
            if (user != null) {
//                call.respondText("Message: $user")

                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update user
        put("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = call.receive<ExposedUser>()
            userService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }

        // Delete user
        delete("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
