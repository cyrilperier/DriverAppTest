package repositories.user

import kotlinx.coroutines.flow.Flow
import services.network.inventory.UserRaw
import network.user.UserRequest
import repositories.user.UserEntity
import repositories.user.UserRepository


class UserRepositoryImpl(
    private val userRequest: UserRequest,
) : UserRepository {


    companion object {
        const val DATE_PATTERN = "dd/MM/yyyy"
    }

    override suspend fun performLogin(email: String, password: String, permanent: Boolean): Boolean {
        return userRequest.performLogin(email,password,permanent)
    }

}