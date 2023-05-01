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
package di.component

import kotlinx.coroutines.CoroutineScope
import ui.screen.detail.PostDetailViewModel
import ui.screen.home.HomeViewModel

/**
 * DI component for providing [ViewModel]s
 */
interface ViewModelComponent {
    fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel
    fun providePostDetailViewModel(coroutineScope: CoroutineScope, postId: Int): PostDetailViewModel
}

internal class DefaultViewModelComponent(
    private val appComponent: DefaultAppComponent,
) : ViewModelComponent {
    override fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel {
        return HomeViewModel(coroutineScope, appComponent.postRepository)
    }

    override fun providePostDetailViewModel(
        coroutineScope: CoroutineScope,
        postId: Int,
    ): PostDetailViewModel {
        return PostDetailViewModel(
            viewModelScope = coroutineScope,
            postId = postId,
            repository = appComponent.postRepository,
        )
    }
}
