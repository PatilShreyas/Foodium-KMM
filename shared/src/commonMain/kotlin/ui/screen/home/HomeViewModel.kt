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
package ui.screen.home

import base.ViewModel
import data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The ViewModel for home screen UI
 */
class HomeViewModel(
    coroutineScope: CoroutineScope,
    private val repository: PostRepository,
) : ViewModel<HomeState>(coroutineScope) {
    private val _state = MutableHomeState(
        posts = emptyList(),
        isLoading = true,
        errorMessage = null,
    )

    override val state = _state.asStateFlow()

    init {
        loadPosts()
    }

    fun refresh() {
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
                        imageUrl = it.imageUrl,
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
