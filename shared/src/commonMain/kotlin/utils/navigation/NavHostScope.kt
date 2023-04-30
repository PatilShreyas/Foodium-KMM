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
import kotlin.reflect.KClass

/**
 * Scope for [NavHost] which will be used to create the destination states in the navigation graph.
 */
interface NavHostScope<STATE : Any> {
    /**
     * Define the Composable [block] for the State [S] in the navigation graph.
     *
     * @param key Class representation of [S]
     */
    fun <S : STATE> OnState(
        key: KClass<S>,
        block: @Composable (entry: NavStackEntry<S>) -> Unit,
    )
}

/**
 * Define the Composable [block] for the State [S] in the navigation graph.
 */
inline fun <reified STATE : Any> NavHostScope<in STATE>.OnState(
    noinline block: @Composable (state: NavStackEntry<STATE>) -> Unit,
) {
    OnState(STATE::class, block)
}
