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
fun uiMode(isInDarkMode: Boolean): UiMode = when (isInDarkMode) {
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
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val toggleRequests = _toggleRequests.asSharedFlow()

    /**
     * Toggles the current UI mode
     */
    fun toggle() = _toggleRequests.tryEmit(Unit)
}

/**
 * Singleton [UiModePreferenceController]
 */
val UiModeController = UiModePreferenceController()
