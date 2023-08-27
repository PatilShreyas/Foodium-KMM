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
package ui.screen.detail

import base.ViewModel
import data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(
    viewModelScope: CoroutineScope,
    postId: Int,
    private val repository: PostRepository,
) : ViewModel<PostDetailState>(viewModelScope) {

    private val _state = MutablePostDetailState(isLoading = true, post = null, errorMessage = null)
    override val state: StateFlow<PostDetailState> = _state.asStateFlow()

    init {
        loadPost(postId)
    }

    private fun loadPost(postId: Int) {
        viewModelScope.launch {
            _state.isLoading = true

            val post = repository.findPostById(postId)
            if (post != null) {
                _state.post = post.let {
                    PostDetailState.Post(
                        title = it.title,
                        content = it.body,
                        author = it.author,
                        imageUrl = it.imageUrl,
                    )
                }
            } else {
                _state.errorMessage = "Can't find the post"
            }
            _state.isLoading = false
        }
    }
}
