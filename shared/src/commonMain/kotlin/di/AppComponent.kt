package di

import data.repository.impl.PostRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import ui.HomeViewModel

interface AppComponent {
    fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel

    companion object {
        private val INSTANCE: AppComponent = DefaultAppComponent()
        fun get() = INSTANCE
    }

    private class DefaultAppComponent: AppComponent {
        private val postRepository by lazy {
            PostRepositoryImpl(httpClient = HttpClient {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            })
        }

        override fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel {
            return HomeViewModel(coroutineScope, postRepository)
        }
    }
}