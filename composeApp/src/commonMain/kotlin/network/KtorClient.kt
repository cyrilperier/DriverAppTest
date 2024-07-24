package network

import io.ktor.client.HttpClient


interface KtorClient {
    val client: HttpClient
}