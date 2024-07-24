package network.user

import kotlinx.coroutines.flow.Flow

interface UserRequest {
    suspend fun performLogin(email: String, password: String, permanent: Boolean): Boolean
}