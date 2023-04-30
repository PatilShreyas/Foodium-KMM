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

import data.model.Post

/**
 * Source of truth for data of Posts from cache
 */
class PostCacheDataSource {
    private val inMemoryCachedPosts = mutableMapOf<Int, Post>()

    /**
     * Adds [posts] in the cache
     */
    suspend fun addAllPosts(posts: List<Post>) {
        inMemoryCachedPosts.putAll(posts.associateBy { it.id })
    }

    /**
     * Returns posts from the cache
     */
    suspend fun getAllPosts(): List<Post> {
        return inMemoryCachedPosts.values.toList()
    }

    /**
     * Finds for post with [id] from the cache
     */
    suspend fun findPostById(id: Int): Post? {
        return inMemoryCachedPosts[id]
    }
}
