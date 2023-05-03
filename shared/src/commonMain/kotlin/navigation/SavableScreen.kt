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
package navigation

private val KEY_SCREEN_NAME = "screen_name"

/**
 * Represents this [Screen] as Map
 */
fun <S : Screen> S.asSavable(): Map<String, String> {
    return when (this) {
        is Screen.PostDetail -> savable<Screen.PostDetail>("postId" to postId.toString())
        is Screen.Home -> savable<Screen.Home>()
        else -> error("Can't save state for screen: ${this@asSavable}. Reason: Undefined")
    }
}

/**
 * Constructs [Screen] from [savable] map data
 */
fun buildScreenFromSavable(savable: Map<String, String>): Screen {
    return when (val screenName = savable[KEY_SCREEN_NAME]) {
        screenName<Screen.Home>() -> Screen.Home
        screenName<Screen.PostDetail>() -> Screen.PostDetail(savable["postId"]!!.toInt())
        else -> error("Can't restore state for screen: $screenName. Reason: Undefined")
    }
}

/**
 * Creates savable Map for screen [S] having entry of [pairs] in the map
 */
private inline fun <reified S : Screen> savable(
    vararg pairs: Pair<String, String>,
) = buildMap<String, String> {
    put(KEY_SCREEN_NAME, screenName<S>())
    putAll(pairs)
}

inline fun <reified SCREEN : Screen> screenName(): String = SCREEN::class.qualifiedName!!
