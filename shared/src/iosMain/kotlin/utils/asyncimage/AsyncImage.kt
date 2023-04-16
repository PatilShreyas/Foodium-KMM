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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
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

/**
 * Implementation of [AsyncImage] for iOS platform.
 */
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
    filterQuality: FilterQuality,
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
                    filterQuality = filterQuality,
                )
            }
        }
    }
}

/**
 * Remembers the state of an image in the composition with [url]
 */
@Composable
private fun rememberImageState(url: String?): State<ImageState> {
    val initialState = if (url == null) ImageState.Error else ImageState.Loading
    return produceState(initialState, key1 = url) {
        if (url != null) {
            value = ImageState.Loading
            runCatching {
                value = ImageState.Success(ImageRepository.getImageBitmapByUrl(url))
            }.getOrElse {
                value = ImageState.Error
            }
        }
    }
}

/**
 * The single source of truth for images
 */
object ImageRepository {
    private val client = HttpClient()

    /**
     * Cache of images loaded in the current session.
     */
    private val inMemoryCache = mutableMapOf<String, ByteArray>()

    /**
     * Loads [ImageBitmap] from the specified [url].
     *
     * If image was already loaded with this [url] then it picks it from [inMemoryCache] otherwise
     * loads it from the network and stores it in the cache.
     */
    suspend fun getImageBitmapByUrl(url: String): ImageBitmap {
        val bytes = inMemoryCache.getOrPut(url) { client.get(url).readBytes() }
        val bitmap = withContext(Dispatchers.Default) {
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        }
        return bitmap
    }
}
