package di.component

import data.repository.impl.PostRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface AppComponent {

    val viewModelComponent: ViewModelComponent

    companion object: AppComponent by DefaultAppComponent()
}

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