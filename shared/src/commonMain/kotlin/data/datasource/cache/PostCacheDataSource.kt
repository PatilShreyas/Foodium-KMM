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
package data.datasource.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import data.model.Post
import dev.shreyaspatil.foodium.db.FoodiumDb
import dev.shreyaspatil.foodium.db.Posts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Source of truth for data of Posts from cache
 */
class PostCacheDataSource(
    private val db: FoodiumDb,
    private val ioDispatcher: CoroutineDispatcher,
) {
    /**
     * Adds [posts] in the cache
     */
    suspend fun replaceAllPosts(posts: List<Post>) = withContext(ioDispatcher) {
        db.postsQueries.transaction {
            db.postsQueries.run {
                deleteAll()
                posts.forEach {
                    addPost(
                        id = it.id.toLong(),
                        title = it.title,
                        author = it.author,
                        body = it.body,
                        imageUrl = it.imageUrl,
                    )
                }
            }
        }
    }

    /**
     * Returns posts from the cache
     */
    fun getAllPosts(): Flow<List<Post>> {
        return db.postsQueries
            .selectAll()
            .asFlow()
            .mapToList(ioDispatcher).map { posts -> posts.map { it.asPost() } }
    }

    /**
     * Finds for post with [id] from the cache
     */
    suspend fun findPostById(id: Int): Post? = withContext(ioDispatcher) {
        db.postsQueries.findById(id.toLong()).executeAsOneOrNull()?.asPost()
    }

    private fun Posts.asPost() = Post(
        id = id.toInt(),
        title = title,
        author = author,
        body = body,
        imageUrl = imageUrl,
    )
}
