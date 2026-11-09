// Lab 7, Step 6: Update the Main Application
package com.example

import com.example.database.DatabaseFactory
import com.example.repositories.PostgresTaskRepository
import com.example.routes.taskRoutes
import com.example.services.TaskService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.module() {
    DatabaseFactory.init()
    val repository = PostgresTaskRepository()
    val service = TaskService(repository)
    install(ContentNegotiation) { json() }
    routing { taskRoutes(service) }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") { module() }.start(wait = true)
}