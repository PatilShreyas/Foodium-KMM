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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

/**
 * Composition local provides [NavStackEntry] scoped to the current destination
 */
val LocalNavStackEntry = compositionLocalOf<NavStackEntry<out Any>> { error("Not implemented") }

/**
 * Provides the [LocalNavStackEntry] in the composition of [content]
 */
@Composable
inline fun <STATE : Any> WithNavStackEntry(
    entry: NavStackEntry<STATE>,
    noinline content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalNavStackEntry provides entry, content = content)
}