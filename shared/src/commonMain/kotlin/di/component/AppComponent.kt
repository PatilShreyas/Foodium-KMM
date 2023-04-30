/**
 * Copyright 2023 Shreyas Patil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package di.component

import data.datasource.cache.PostCacheDataSource
import data.datasource.remote.PostRemoteDataSource
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

    companion object : AppComponent by DefaultAppComponent()
}

/**
 * Default implementation of [AppComponent]
 */
internal class DefaultAppComponent : AppComponent {
    val postRepository by lazy {
        PostRepositoryImpl(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource,
        )
    }

    private val remoteDataSource by lazy { PostRemoteDataSource(provideHttpClient()) }
    private val cacheDataSource by lazy { PostCacheDataSource() }

    private fun provideHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override val viewModelComponent: ViewModelComponent by lazy { DefaultViewModelComponent(this) }
}
