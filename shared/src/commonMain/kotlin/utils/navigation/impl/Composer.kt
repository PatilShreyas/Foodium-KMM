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
import utils.navigation.NavStackEntry

/**
 * Composer of the destination [STATE] in the navigation graph.
 * [STATE] is a super type of [BASE] state.
 */
fun interface Composer<STATE : BASE, BASE : Any> {
    /**
     * Composes destination with the [entry]
     */
    @Composable
    fun compose(entry: NavStackEntry<STATE>)
}

/**
 * Composer of the destination [BASE] in the navigation graph.
 * [BASE] is a super type of [BASE] state.
 */
fun <STATE : BASE, BASE : Any> Composer(
    block: @Composable (NavStackEntry<STATE>) -> Unit,
): Composer<STATE, BASE> {
    return ComposerImpl(block)
}

/**
 * Default implementation of [Composer]
 */
class ComposerImpl<STATE : BASE, BASE : Any>(
    val block: @Composable (NavStackEntry<STATE>) -> Unit,
) : Composer<STATE, BASE> {
    @Composable
    override fun compose(entry: NavStackEntry<STATE>) {
        block(entry)
    }
}
