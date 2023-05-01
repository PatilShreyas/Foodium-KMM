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
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import utils.appfinisher.AppFinisher
import utils.appfinisher.LocalAppFinisher

@Composable
fun MainView() {
    CompositionLocalProvider(LocalAppFinisher provides androidAppFinisher()) {
        App()
    }
}

@Composable
fun androidAppFinisher(): AppFinisher {
    val activity = LocalContext.current.findActivity()
    return object : AppFinisher {
        override fun finish() {
            activity.finish()
        }
    }
}

/**
 * Traverses through this [Context] and finds [Activity] wrapped inside it.
 */
private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Unable to retrieve Activity from the current context")
}
