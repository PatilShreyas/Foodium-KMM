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
package base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow

/**
 * The base state-holder model for the UI for performing UI-business operations
 *
 * @property viewModelScope The coroutine scope which will be bound with the UI lifecycle
 */
abstract class ViewModel<STATE>(protected val viewModelScope: CoroutineScope) {
    /**
     * The reactive state of a UI
     */
    abstract val state: StateFlow<STATE>

    /**
     * Invoked when the [ViewModel] is no longer needed. Do clean-up stuff here.
     */
    open fun onCleared() {}
}

/**
 * The factory for creating instance of [ViewModel] in the composition.
 *
 * @param provideViewModel Lambda factory for creating ViewModel
 */
@Composable
fun <VM : ViewModel<STATE>, STATE> viewModelFactory(provideViewModel: (CoroutineScope) -> VM): VM {
    val viewModelScope = remember { ViewModelCoroutineScope() }
    val viewModel = rememberSaveable { provideViewModel(viewModelScope) }

    DisposableEffect(true) {
        onDispose {
            viewModelScope.cancel()
            viewModel.onCleared()
        }
    }

    return viewModel
}
