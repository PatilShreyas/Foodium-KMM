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

import data.datasource.cache.PostCacheDataSource
import data.datasource.remote.PostRemoteDataSource
import data.model.Post
import data.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Default implementation for [PostRepository] that refers to the [PostCacheDataSource] as a
 * single source of truth for database and syncs it with the latest posts from [PostRemoteDataSource]
 */
internal class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteDataSource,
    private val cacheDataSource: PostCacheDataSource,
) : PostRepository {
    override fun getAllPosts(): Flow<List<Post>> {
        return flow {
            // 1. Provide initial cached resource immediately
            emit(cacheDataSource.getAllPosts().first())

            // 2. Get data from a network and put in cache
            cacheDataSource.replaceAllPosts(remoteDataSource.getAllPosts())

            // 3. Observe cached data as a single source of truth only
            emitAll(cacheDataSource.getAllPosts())
        }
    }

    override suspend fun findPostById(id: Int): Post? {
        return cacheDataSource.findPostById(id)
    }
}
