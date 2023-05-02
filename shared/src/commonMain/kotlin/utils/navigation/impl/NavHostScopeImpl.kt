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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import utils.navigation.NavHostScope
import utils.navigation.NavStackEntry
import utils.navigation.NavigationController
import utils.navigation.WithNavStackEntry
import kotlin.reflect.KClass

/**
 * Default implementation of [NavHostScope]
 */
internal class NavHostScopeImpl<STATE : Any>(
    private val navigationController: NavigationController<STATE>,
    private val coroutineScope: CoroutineScope,
) : NavHostScope<STATE> {
    private val composerByState = mutableMapOf<KClass<out STATE>, Composer<out STATE, STATE>>()

    private val inMemoryValueStore = InMemoryNavStackEntryStore<STATE>()

    init {
        observeNavigationEventsForValueStoreCleanup()
    }

    @Suppress("UNCHECKED_CAST")
    @Composable
    fun compose(state: STATE) {
        val stateClass = state::class
        val composable = composerByState[stateClass] as? Composer<STATE, STATE>
        if (composable != null) {
            val entry = inMemoryValueStore.get(forState = state)
            WithNavStackEntry(entry) {
                composable.compose(entry = entry)
            }
        } else {
            throw IllegalStateException("State is not defined for type '$stateClass'")
        }
    }

    override fun <S : STATE> OnState(
        key: KClass<S>,
        block: @Composable (entry: NavStackEntry<S>) -> Unit,
    ) {
        composerByState[key] = Composer(movableContentOf(block))
    }

    private fun observeNavigationEventsForValueStoreCleanup() {
        coroutineScope.launch {
            navigationController.events.collect { navEvent ->
                val stateForDisposal = when (navEvent) {
                    is NavigationEvents.OnPopUp<STATE> -> navEvent.poppedState
                    is NavigationEvents.OnStackEmpty<STATE> -> navEvent.previousState
                    else -> null
                }
                if (stateForDisposal != null) {
                    inMemoryValueStore.dispose(forState = stateForDisposal)
                }
            }
        }
    }
}

@Composable
internal fun <STATE : Any> rememberNavHostScope(
    navigationController: NavigationController<STATE>,
    block: NavHostScope<STATE>.() -> Unit,
): NavHostScopeImpl<STATE> {
    val coroutineScope = rememberCoroutineScope()

    return remember(navigationController, coroutineScope, block) {
        NavHostScopeImpl<STATE>(
            navigationController = navigationController,
            coroutineScope = coroutineScope,
        ).apply(block)
    }
}
