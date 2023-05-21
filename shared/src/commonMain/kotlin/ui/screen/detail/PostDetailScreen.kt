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
package ui.screen.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.component.ErrorContent
import ui.component.PostGraphicImage
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer
import kotlin.math.max
import kotlin.math.min

@Composable
fun PostDetailScreen(viewModel: PostDetailViewModel, onNavigateUp: () -> Unit) {
    val state by viewModel.state.collectAsState()

    PostDetailContent(
        isLoading = state.isLoading,
        post = state.post,
        errorMessage = state.errorMessage,
        onBackClick = onNavigateUp,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostDetailContent(
    isLoading: Boolean,
    post: PostDetailState.Post?,
    errorMessage: String?,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBarContent(
                headerImageUrl = post?.imageUrl,
                title = post?.title ?: "",
                scrollState = scrollState,
                onBackClick = onBackClick,
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                ),
            )
        },
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val bodyMinHeight = remember(maxHeight) { maxHeight + 250.dp }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(it),
            ) {
                if (errorMessage != null) {
                    ErrorContent(errorMessage)
                } else {
                    PostDetailContent(
                        isLoading = isLoading,
                        title = post?.title ?: "",
                        author = post?.author ?: "",
                        content = post?.content ?: "",
                        modifier = Modifier.padding(16.dp).heightIn(min = bodyMinHeight),
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBarContent(
    headerImageUrl: String?,
    title: String,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    modifier: Modifier,
) {
    val imageHeight by animateSizePerScrollState(250.dp, scrollState)
    val alphaPerScroll by animateAlphaPerScrollState(scrollState)

    Box(modifier.fillMaxWidth()) {
        PostGraphicImage(
            url = headerImageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                },
        )

        TopAppBar(
            title = {
                if (imageHeight < 68.dp) {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.alpha(alphaPerScroll),
                        color = Color.White,
                    )
                }
            },
            backgroundColor = Color.Black.copy(alpha = alphaPerScroll),
            elevation = 0.dp,
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBackIosNew, "Navigate back", tint = Color.White)
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun PostDetailContent(
    isLoading: Boolean,
    title: String,
    author: String,
    content: String,
    modifier: Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
        )
        Text(
            text = "~ $author",
            style = MaterialTheme.typography.overline,
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.requiredWidthIn(min = 120.dp).placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body1,
            fontFamily = FontFamily.Serif,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.fillMaxWidth().requiredHeightIn(min = 240.dp).placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
        )
    }
}

@Composable
fun animateSizePerScrollState(
    initialSize: Dp,
    scrollState: ScrollState,
): State<Dp> = produceState(initialSize, key1 = scrollState.value) {
    value = Dp(max(0f, initialSize.value - (scrollState.value / 2f)))
}

@Composable
fun animateAlphaPerScrollState(
    scrollState: ScrollState,
): State<Float> = produceState(0f, key1 = scrollState.value) {
    value = min(1f, (scrollState.value / 600f))
}
