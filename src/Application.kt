package com.quotesapi

import api.routes.*
import com.quotesapi.repository.*
import io.ktor.application.Application
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.features.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.init()

    val db = QuotesRepository()

    routing {
        quotes(db)
    }
}

