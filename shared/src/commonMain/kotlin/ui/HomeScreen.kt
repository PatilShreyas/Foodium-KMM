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
package ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import base.viewModelFactory
import di.component.AppComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.accompanist.placeholder.PlaceholderHighlight
import utils.accompanist.placeholder.placeholder
import utils.accompanist.placeholder.shimmer
import utils.asyncimage.AsyncImage

@Composable
fun HomeScreen() {
    val viewModel = rememberHomeViewModel()
    val state by viewModel.state.collectAsState()

    HomeContent(state.isLoading, state.posts, state.errorMessage)
}

@Composable
fun HomeContent(isLoading: Boolean, posts: List<HomeState.Post>, errorMessage: String?) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Foodium") })
        if (errorMessage != null) {
            ErrorContent(errorMessage)
        } else {
            Crossfade(isLoading, animationSpec = tween(500)) { isLoading ->
                LazyColumn(Modifier.fillMaxSize()) {
                    items(
                        items = if (isLoading) loadingPostCards else posts,
                        key = { it.id },
                    ) { post ->
                        PostCard(
                            isLoading = isLoading,
                            post = post,
                        )
                    }
                }
            }
        }
    }
}

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

            AsyncImage(
                imageUrl = post.imageUrl,
                contentDescription = "",
                modifier = Modifier.size(100.dp)
                    .placeholder(isLoading, highlight = PlaceholderHighlight.shimmer()),
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
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ErrorContent(errorMessage: String) {
    Image(painterResource("icon_empty.xml"), contentDescription = null)
    Spacer(Modifier.height(16.dp))
    Text(
        "Something went wrong!",
        color = Color.Red,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    )
    Spacer(Modifier.height(8.dp))
    Text(
        errorMessage,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun rememberHomeViewModel(): HomeViewModel {
    return viewModelFactory { scope -> AppComponent.viewModelComponent.provideHomeViewModel(scope) }
}

/**
 * Dummy posts for showing shimmer animation while posts are loading
 */
private val loadingPostCards = List(6) { HomeState.Post(it, "", "", "") }
