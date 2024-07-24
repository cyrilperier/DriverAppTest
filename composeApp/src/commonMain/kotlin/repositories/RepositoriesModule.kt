package repositories

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import repositories.user.UserRepository
import repositories.user.UserRepositoryImpl


val repositoriesModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
}
