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

import androidx.compose.runtime.mutableStateMapOf
import utils.navigation.NavStackEntry

/**
 * Default implementation of [NavStackEntry]
 */
internal data class NavStackEntryImpl<STATE : Any>(override val state: STATE) : NavStackEntry<STATE> {
    private val store = mutableStateMapOf<Any, StoreValueHolder<*>>()
    private var isDisposed = false

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getOrPut(key: Any, compute: () -> T, onDispose: (T) -> Unit): T {
        if (isDisposed) {
            error("The NavStackEntry(state=$state) is already disposed")
        }
        val currentValue = store[key] as? StoreValueHolder<T>
        if (currentValue != null) {
            return currentValue.apply { onCleared = onDispose }.value
        }
        val valueHolder = StoreValueHolder(compute(), onDispose)
        store[key] = valueHolder
        return valueHolder.value
    }

    /**
     * Disposes all values associated of a [state]
     */
    internal fun dispose() {
        isDisposed = true
        store.values.forEach { holder -> holder.dispose() }
        store.clear()
    }

    /**
     * Value and its disposer holder
     */
    private class StoreValueHolder<T : Any>(val value: T, var onCleared: (T) -> Unit) {
        fun dispose() {
            onCleared(value)
        }
    }
}
