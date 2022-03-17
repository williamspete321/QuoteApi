package api.routes

import com.quotesapi.api.requests.*
import com.quotesapi.repository.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.quotes(db: Repository) {
    route("/quote") {
        get {
            if (db.quotes().isNotEmpty()) {
                call.respond(db.quotes())
            } else {
                call.respondText("No quotes found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val quote = db.quoteById(id) ?: return@get call.respondText(
                    "No quote with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(quote)
        }
        get("/random") {
            val quote = db.quoteByRandom() ?: return@get call.respondText(
                "No quote available",
                status = HttpStatusCode.NotFound
            )
            call.respond(quote)
        }
        post {
            try {
                val request = call.receive<QuotesApiRequest>()
                val quote = db.add(request.value, request.url)
                if (quote != null) {
                    call.respond(quote)
                } else {
                    call.respondText("Invalid data received", status = HttpStatusCode.InternalServerError)
                }
            } catch (e: Throwable) {
                call.application.environment.log.error(e.localizedMessage)
                call.respondText("Invalid data received", status = HttpStatusCode.BadRequest)
            }
        }
    }
}