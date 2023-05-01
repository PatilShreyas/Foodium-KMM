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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.screen.home.HomeState
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer

/**
 * Displays card having post details
 *
 * @param isLoading If true, shimmer placeholder will be displayed in place of the card's contents
 * @param post Post to be displayed
 */
@Composable
fun PostCard(isLoading: Boolean, post: HomeState.Post, modifier: Modifier = Modifier) {
    Card(modifier.padding(vertical = 1.dp)) {
        Row(Modifier.fillMaxWidth().padding(16.dp)) {
            Column(Modifier.fillMaxWidth(0.7f).padding(end = 24.dp)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
                        .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "~ ${post.author}",
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
                )
            }

            PostGraphicImage(
                url = post.imageUrl,
                modifier = modifier.size(100.dp)
                    .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
            )
        }
    }
}
