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
package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer
import utils.asyncimage.AsyncImage

/**
 * The post's graphic image which will be loaded from [url]
 */
@Composable
fun PostGraphicImage(url: String?, modifier: Modifier) {
    AsyncImage(
        imageUrl = url,
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop,
        loadingPlaceholder = {
            Box(
                Modifier.matchParentSize()
                    .placeholder(true, highlight = PlaceholderHighlight.shimmer()),
            )
        },
        errorPlaceholder = {
            Box(Modifier.matchParentSize().background(Color.DarkGray)) {
                Text(
                    text = "X",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 32.sp,
                    color = Color.White,
                )
            }
        },
    )
}
