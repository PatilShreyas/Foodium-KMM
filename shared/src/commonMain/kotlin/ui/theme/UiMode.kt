package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.produceState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class UiMode {
    LIGHT, DARK
}

/**
 * Remembers the [UiMode] for the application. First of all, it respects the system's UI mode.
 * Then if user requests change of mode, that will be updated and new state will be updated
 */
@Composable
fun rememberUiMode(): State<UiMode> {
    val systemIsInDarkMode = isSystemInDarkTheme()

    val uiModeController = LocalUiModePreferenceController.current

    return produceState(uiMode(systemIsInDarkMode)) {
        uiModeController.toggleRequests.collect {
            value = value.oppositeMode
        }
    }
}

/**
 * Returns [UiMode.DARK] if [isInDarkMode] is true. Else [UiMode.LIGHT]
 */
fun uiMode(isInDarkMode: Boolean): UiMode = when(isInDarkMode) {
    true -> UiMode.DARK
    false -> UiMode.LIGHT
}

/**
 * Returns opposite mode from this [UiMode].
 * i.e. if current mode is [UiMode.LIGHT] then returns [UiMode.DARK] and vice versa.
 */
val UiMode.oppositeMode get() = when (this) {
    UiMode.LIGHT -> UiMode.DARK
    UiMode.DARK -> UiMode.LIGHT
}

val LocalUiModePreferenceController =
    compositionLocalOf<UiModePreferenceController> { error("Not provided") }

/**
 * Controller for central UI mode preference
 */
class UiModePreferenceController {
    private val _toggleRequests = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toggleRequests = _toggleRequests.asSharedFlow()

    /**
     * Toggles the current UI mode
     */
    fun toggle() = _toggleRequests.tryEmit(Unit)
}