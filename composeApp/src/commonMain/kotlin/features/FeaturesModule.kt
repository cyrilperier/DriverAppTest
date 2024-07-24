package features

import features.login.LoginViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featuresModule = module {
    factoryOf(::LoginViewModel)
}