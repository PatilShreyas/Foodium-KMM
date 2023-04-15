package utils.asyncimage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image

@Composable
actual fun AsyncImage(
    imageUrl: String?,
    loadingPlaceholder: @Composable BoxScope.() -> Unit,
    errorPlaceholder: @Composable BoxScope.() -> Unit,
    contentDescription: String?,
    modifier: Modifier,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    colorFilter: ColorFilter?,
    filterQuality: FilterQuality
) {
    val imageState by rememberImageState(imageUrl)

    Box(modifier = modifier) {
        when (val state = imageState) {
            ImageState.Error -> {
                errorPlaceholder()
            }
            ImageState.Loading -> {
                loadingPlaceholder()
            }
            is ImageState.Success -> {
                Image(
                    bitmap = state.imageBitmap,
                    contentDescription = contentDescription,
                    modifier = modifier,
                    alignment = alignment,
                    contentScale = contentScale,
                    alpha = alpha,
                    colorFilter = colorFilter,
                    filterQuality = filterQuality
                )
            }
        }
    }
}

@Composable
private fun rememberImageState(url: String?): State<ImageState> {
    val initialState = if (url == null) ImageState.Error else ImageState.Loading
    return produceState(initialState, key1 = url) {
        if (url != null) {
            value = ImageState.Loading
            runCatching {
                value = ImageState.Success(ImageRepository.getImageBitmap(url))
            }.getOrElse {
                value = ImageState.Error
            }
        }
    }
}

object ImageRepository {
    val client = HttpClient()

    private val inMemoryCache = mutableMapOf<String, ByteArray>()

    suspend fun getImageBitmap(url: String): ImageBitmap {
        val bytes = inMemoryCache.getOrPut(url) { client.get(url).readBytes() }
        val bitmap = withContext(Dispatchers.Default) {
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        }
        return bitmap
    }
}