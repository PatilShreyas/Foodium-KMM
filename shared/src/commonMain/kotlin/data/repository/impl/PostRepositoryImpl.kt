package data.repository.impl

import data.model.Post
import data.repository.PostRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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