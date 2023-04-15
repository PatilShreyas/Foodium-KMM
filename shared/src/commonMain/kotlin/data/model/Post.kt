package data.model

@kotlinx.serialization.Serializable
data class Post(
    val id: Int,
    val title: String,
    val author: String,
    val body: String,
    val imageUrl: String
)