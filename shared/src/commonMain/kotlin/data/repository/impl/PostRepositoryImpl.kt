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
package data.repository.impl

import data.model.Post
import data.repository.PostRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Default implementation for [PostRepository] that refers to the network as a data source
 */
internal class PostRepositoryImpl(private val httpClient: HttpClient) : PostRepository {
    override suspend fun getAllPosts(): List<Post> {
        return httpClient.get(GET_ALL_POSTS_URL).bodyAsText().let { json ->
            Json.decodeFromString(json)
        }
    }

    companion object {
        private const val BASE_URL = "https://patilshreyas.github.io"

        private const val GET_ALL_POSTS_URL = "$BASE_URL/DummyFoodiumApi/api/posts/"
    }
}
