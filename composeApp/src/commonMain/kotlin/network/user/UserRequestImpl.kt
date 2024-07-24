package network.user


import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import network.KtorClient
import io.ktor.client.request.setBody

import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val permanent: Boolean
)

class UserRequestImpl(
    private val ktorClient: KtorClient
) : UserRequest {
    override suspend fun performLogin(email: String, password: String, permanent: Boolean): Boolean {
        try {
            val request =
                ktorClient.client.post("https://api.alpha.wa-labs.com/v3/account/users/signin")
                {
                    contentType(ContentType.Application.Json)
                    setBody(
                        LoginRequest(email, password, permanent)
                    )
                }
            return request.status.isSuccess()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
    }
    companion object {
        const val INVENTORY_ENDPOINT = "/inventory"
    }
}