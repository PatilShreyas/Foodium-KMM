package ui

import androidx.compose.runtime.Immutable
import dev.shreyaspatil.mutekt.core.annotations.GenerateMutableModel

/**
 * The UI state of Home screen
 */
@GenerateMutableModel
interface HomeState {
    val isLoading: Boolean
    val posts: List<Post>
    val errorMessage: String?

    @Immutable
    data class Post(val id: Int, val title: String, val author: String, val imageUrl: String?)
}


