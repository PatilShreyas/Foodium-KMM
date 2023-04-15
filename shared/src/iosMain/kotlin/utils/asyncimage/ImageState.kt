package utils.asyncimage

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap

@Immutable
sealed interface ImageState {
    @Immutable
    object Loading : ImageState

    @Immutable
    object Error : ImageState

    @Immutable
    data class Success(val imageBitmap: ImageBitmap) : ImageState
}