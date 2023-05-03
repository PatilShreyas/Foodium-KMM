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

inline fun <reified SCREEN : Screen> screenName(): String = SCREEN::class.qualifiedName!!

/**
 * Represents this [Screen] as Map
 */
fun Screen.asSavable(): Map<String, String> {
    val screen = this

    return buildMap {
        put(KEY_SCREEN_NAME, screenName<Screen>())

        val data = when (screen) {
            is Screen.Detail -> mapOf("postId" to screen.postId.toString())
            is Screen.Home -> emptyMap()
        }

        putAll(data)
    }
}

/**
 * Constructs [Screen] from [savable] map data
 */
fun buildScreenFromSavable(savable: Map<String, String>): Screen {
    return when (val screenName = savable[KEY_SCREEN_NAME]) {
        screenName<Screen.Home>() -> Screen.Home
        screenName<Screen.Detail>() -> Screen.Detail(savable["postId"]!!.toInt())
        else -> error("Can't restore state for $screenName because it's not defined.")
    }
}
