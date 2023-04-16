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
package utils.asyncimage

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Represents the state of an image
 */
@Immutable
sealed interface ImageState {
    /**
     * Represents that image is currently loading
     */
    @Immutable
    object Loading : ImageState

    /**
     * Represents that image is failed to render
     */
    @Immutable
    object Error : ImageState

    /**
     * Represents that image is successfully retrieved
     *
     * @property imageBitmap The bitmap representation of Image.
     */
    @Immutable
    data class Success(val imageBitmap: ImageBitmap) : ImageState
}
