// Lab 7
package com.example.routes

import com.example.models.CreateTaskRequest
import com.example.services.TaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRoutes(service: TaskService) {
    route("/api/tasks") {
        get { call.respond(service.getAllTasks()) }
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run { call.respond(HttpStatusCode.BadRequest); return@get }
            val t = service.getTask(id)
            if (t != null) call.respond(t) else call.respond(HttpStatusCode.NotFound)
        }
        post {
            val req = call.receive<CreateTaskRequest>()
            call.respond(HttpStatusCode.Created, service.createTask(req))
        }
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run { call.respond(HttpStatusCode.BadRequest); return@put }
            val req = call.receive<CreateTaskRequest>()
            val t = service.updateTask(id, req)
            if (t != null) call.respond(t) else call.respond(HttpStatusCode.NotFound)
        }
        patch("/{id}/toggle") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run { call.respond(HttpStatusCode.BadRequest); return@patch }
            val t = service.toggleTask(id)
            if (t != null) call.respond(t) else call.respond(HttpStatusCode.NotFound)
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run { call.respond(HttpStatusCode.BadRequest); return@delete }
            if (service.deleteTask(id)) call.respond(HttpStatusCode.NoContent) else call.respond(HttpStatusCode.NotFound)
        }
    }
}