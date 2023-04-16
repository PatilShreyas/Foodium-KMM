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