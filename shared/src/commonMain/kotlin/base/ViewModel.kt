package base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

abstract class ViewModel<STATE>(protected val viewModelScope: CoroutineScope) {
    abstract val state: StateFlow<STATE>

    open fun onCleared() {}
}

@Composable
fun <VM: ViewModel<STATE>, STATE> viewModelFactory(provideViewModel: (CoroutineScope) -> VM): VM {
    val scope = rememberCoroutineScope()
    val viewModel = remember { provideViewModel(scope) }

    DisposableEffect(true) {
        onDispose { viewModel.onCleared() }
    }

    return viewModel
}