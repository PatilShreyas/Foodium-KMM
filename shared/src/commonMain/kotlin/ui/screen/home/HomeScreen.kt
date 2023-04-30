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
package ui.screen.home

import Screen
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ui.component.ErrorContent
import ui.component.PostCard
import utils.navigation.NavStackEntry
import utils.navigation.rememberInNavStack

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int) -> Unit,
    navStackEntry: NavStackEntry<Screen.Home>,
) {
    val state by viewModel.state.collectAsState()
    val lazyListState = navStackEntry.rememberInNavStack(
        key = "scrollState",
        compute = { LazyListState() },
    )

    HomeContent(
        isLoading = state.isLoading,
        posts = state.posts,
        errorMessage = state.errorMessage,
        onNavigateToDetail = onNavigateToDetail,
        lazyListState = lazyListState,
    )
}

@Composable
fun HomeContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    errorMessage: String?,
    onNavigateToDetail: (Int) -> Unit,
    lazyListState: LazyListState,
) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Foodium") })

        if (errorMessage != null) {
            ErrorContent(errorMessage)
        } else {
            Crossfade(isLoading, animationSpec = tween(500)) { isLoading ->
                PostListContent(lazyListState, isLoading, posts, onNavigateToDetail)
            }
        }
    }
}

@Composable
private fun PostListContent(
    lazyListState: LazyListState,
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    onNavigateToDetail: (Int) -> Unit,
) {
    LazyColumn(Modifier.fillMaxSize(), state = lazyListState) {
        items(
            items = if (isLoading) loadingPostCards else posts,
            key = { it.id },
        ) { post ->
            PostCard(
                isLoading = isLoading,
                post = post,
                modifier = Modifier.clickable(enabled = !isLoading) {
                    onNavigateToDetail(post.id)
                },
            )
        }
    }
}

/**
 * Dummy posts for showing shimmer animation while posts are loading
 */
private val loadingPostCards = List(6) { HomeState.Post(it, "", "", "") }
