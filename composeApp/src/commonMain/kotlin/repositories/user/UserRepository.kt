package repositories.user

interface UserRepository {
    suspend fun performLogin(email: String, password: String, permanent: Boolean): Boolean
}