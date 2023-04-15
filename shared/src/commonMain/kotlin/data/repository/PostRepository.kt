package data.repository

import data.model.Post

interface PostRepository {
    suspend fun getAllPosts(): List<Post>
}

