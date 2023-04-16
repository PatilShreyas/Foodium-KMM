package data.repository

import data.model.Post

/**
 * The single source of truth for Posts
 */
interface PostRepository {
    /**
     * Retrieves all the posts
     */
    suspend fun getAllPosts(): List<Post>
}

