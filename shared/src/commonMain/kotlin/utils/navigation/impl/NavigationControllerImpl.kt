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
package utils.navigation.impl

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import utils.navigation.NavigationController

/**
 * Default implementation of [NavigationController]
 */
internal class NavigationControllerImpl<STATE : Any>(
    private val coroutineScope: CoroutineScope = MainScope(),
    private val stack: Stack<STATE> = Stack(),
) : NavigationController<STATE> {

    private val _events = MutableSharedFlow<NavigationEvents<STATE>>()
    override val events = _events.asSharedFlow()

    override val currentState: STATE? get() = stack.top

    override val backStackSize: Int get() = stack.size

    val backStack get() = stack.items

    private val mutex = Mutex()

    override fun navigateTo(newState: STATE) {
        coroutineScope.launch {
            mutex.withLock {
                val top = stack.top
                stack.push(newState)
                val nextEvent = if (top == null) {
                    NavigationEvents.InitialState(newState)
                } else {
                    NavigationEvents.OnNavigateTo(newState)
                }
                _events.emit(nextEvent)
            }
        }
    }

    override fun navigateUp() {
        coroutineScope.launch {
            mutex.withLock {
                val poppedState = stack.pop()
                val nextState = stack.top
                val nextEvent = if (nextState == null) {
                    NavigationEvents.OnStackEmpty(poppedState)
                } else {
                    NavigationEvents.OnPopUp(nextState, poppedState)
                }
                _events.emit(nextEvent)
            }
        }
    }
}

@Immutable
sealed interface NavigationEvents<STATE> {
    val state: STATE?

    @Immutable
    data class InitialState<STATE>(override val state: STATE) : NavigationEvents<STATE>

    @Immutable
    data class OnNavigateTo<STATE>(override val state: STATE) : NavigationEvents<STATE>

    @Immutable
    data class OnPopUp<STATE>(
        override val state: STATE,
        val poppedState: STATE,
    ) : NavigationEvents<STATE>

    @Immutable
    class OnStackEmpty<STATE>(val previousState: STATE) : NavigationEvents<STATE> {
        override val state: STATE? = null

        override fun equals(other: Any?): Boolean {
            return other is OnStackEmpty<*>
        }

        override fun hashCode(): Int {
            return this::class.hashCode()
        }
    }
}
