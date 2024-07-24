package network

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import network.user.UserRequest
import network.user.UserRequestImpl

val networkModule = module {
    singleOf(::MDWKtorClient) bind KtorClient::class
    singleOf(::UserRequestImpl) bind UserRequest::class
}

