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
package utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.SharedFlow
import utils.navigation.impl.NavigationControllerImpl
import utils.navigation.impl.NavigationEvents

/**
 * NavController manages app navigation within a [NavHost].
 */
interface NavigationController<STATE> {
    /**
     * Count of items in the backstack
     */
    val backStackSize: Int

    /**
     * The current destination state in the navigation graph
     */
    val currentState: STATE?

    /**
     * Navigate to a [newState] in the current Navigation graph
     */
    fun navigateTo(newState: STATE)

    /**
     * Pops the current destination from Navigation graph
     */
    fun navigateUp()

    /**
     * Reactive stream for [NavigationEvents]
     */
    val events: SharedFlow<NavigationEvents<STATE>>
}

/**
 * Remembers the navigation controller in this composition scope.
 */
@Composable
fun <STATE : Any> rememberNavigationController(): NavigationController<STATE> {
    return remember(Unit) { NavigationControllerImpl() }
}
