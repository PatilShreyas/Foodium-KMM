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
package utils.appfinisher

import androidx.compose.runtime.compositionLocalOf

/**
 * The controller for exiting from application
 */
interface AppFinisher {
    /**
     * Finishes the activity and exits
     */
    fun finish()
}

val LocalAppFinisher = compositionLocalOf<AppFinisher> { error("Implementation not provided") }
