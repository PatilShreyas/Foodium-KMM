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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import utils.appfinisher.AppFinisher
import utils.appfinisher.LocalAppFinisher

fun MainViewController() = ComposeUIViewController {
    CompositionLocalProvider(LocalAppFinisher provides iosAppFinisher()) {
        App()
    }
}

fun iosAppFinisher(): AppFinisher {
    // There is no API provided for gracefully terminating an iOS application.
    // In iOS, the user presses the Home button to close applications. Should your application have
    // conditions in which it cannot provide its intended function, the recommended approach is to
    // display an alert for the user that indicates the nature of the problem and possible actions
    // the user could take — turning on WiFi, enabling Location Services, etc. Allow the user to
    // terminate the application at their own discretion.
    // See this for more info: https://developer.apple.com/library/archive/qa/qa1561/_index.html

    return object : AppFinisher {
        override fun finish() {
            // no-op
        }
    }
}
