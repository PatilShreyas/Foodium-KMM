package base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import io.ktor.utils.io.core.Closeable
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow

/**
 * The base state-holder model for the UI for performing UI-business operations
 *
 * @property viewModelScope The coroutine scope which will be bound with the UI lifecycle
 */
abstract class ViewModel<STATE>(protected val viewModelScope: CoroutineScope) {
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
fun <VM: ViewModel<STATE>, STATE> viewModelFactory(provideViewModel: (CoroutineScope) -> VM): VM {
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