package di.component

import data.repository.impl.PostRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * App component for holding app dependencies
 */
interface AppComponent {

    /**
     * View model component for providing [ViewModel]s
     */
    val viewModelComponent: ViewModelComponent

    companion object: AppComponent by DefaultAppComponent()
}

/**
 * Default implementation of [AppComponent]
 */
internal class DefaultAppComponent : AppComponent {
    val postRepository by lazy {
        PostRepositoryImpl(httpClient = provideHttpClient())
    }

    private fun provideHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override val viewModelComponent: ViewModelComponent by lazy { DefaultViewModelComponent(this) }
}