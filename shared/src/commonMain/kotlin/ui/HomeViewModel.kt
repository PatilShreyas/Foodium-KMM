package ui

import base.ViewModel
import data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The ViewModel for home screen UI
 */
class HomeViewModel(
    coroutineScope: CoroutineScope,
    private val repository: PostRepository
) : ViewModel<HomeState>(coroutineScope) {
    private val _state = MutableHomeState(
        posts = emptyList(),
        isLoading = true,
        errorMessage = null
    )

    override val state = _state.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _state.isLoading = true
            try {
                val posts = repository.getAllPosts().map {
                    HomeState.Post(
                        id = it.id,
                        title = it.title,
                        author = it.author,
                        imageUrl = it.imageUrl
                    )
                }
                _state.posts = posts
            } catch (e: Throwable) {
                _state.errorMessage = "Error occurred: ${e.message}"
            } finally {
                _state.isLoading = false
            }
        }
    }
}
