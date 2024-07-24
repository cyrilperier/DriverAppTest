package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import services.core.logger.logError

@OptIn(ExperimentalSerializationApi::class)
class MDWKtorClient(
) : KtorClient {
    override val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = true
                isLenient = true
            })
        }
        defaultRequest {
            header(HttpHeaders.ContentType, APPLICATION_JSON)
        }
    }

    companion object {
        const val BEARER = "Bearer"
        const val APPLICATION_JSON = "application/json"
        const val CLIENT_ID = "client_id"
        const val CLIENT_SECRET = "client_secret"
    }
}

inline fun <reified T> HttpResponse.handleHttpResponse(): Flow<T> {
    return if (this.status.isSuccess()) {
        flow {
            val body: T = this@handleHttpResponse.body()
            emit(body)
        }.catch {
            it.logError()
            emptyFlow<Unit>()
        }
    } else {
        val httpError = "${this.status.value} ${this.status.description}"
        flow {
            throw Exception(httpError)
        }
    }
}