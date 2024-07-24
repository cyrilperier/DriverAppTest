import features.featuresModule
import navigation.navigationModule
import network.networkModule
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import repositories.repositoriesModule

class InitKoin: KoinComponent {
    fun initDependencies() = startKoin {
        modules(
            repositoriesModule,
            navigationModule,
            featuresModule,
            networkModule
        )
    }


}

