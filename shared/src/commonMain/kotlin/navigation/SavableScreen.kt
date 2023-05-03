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

