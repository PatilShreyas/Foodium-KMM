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

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import ui.component.ErrorContent
import ui.component.PostCard
import ui.theme.LocalUiModePreferenceController
import ui.theme.UiMode
import ui.theme.rememberUiMode
import utils.navigation.rememberInNavStack

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    HomeContent(
        isLoading = state.isLoading,
        posts = state.posts,
        errorMessage = state.errorMessage,
        onNavigateToDetail = onNavigateToDetail,
        onRefresh = viewModel::refresh,
    )
}

@Composable
fun HomeContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    errorMessage: String?,
    onNavigateToDetail: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        HomeAppBar()

        if (errorMessage != null) {
            ErrorContent(errorMessage)
        } else {
            Crossfade(isLoading, animationSpec = tween(500)) { isLoading ->
                PostListContent(
                    isLoading = isLoading,
                    posts = posts,
                    onNavigateToDetail = onNavigateToDetail,
                    onRefresh = onRefresh,
                )
            }
        }
    }
}

@Composable
private fun HomeAppBar() {
    TopAppBar(
        title = { Text("Foodium") },
        backgroundColor = Color.Black,
        actions = {
            val controller = LocalUiModePreferenceController.current
            val uiMode by rememberUiMode()

            IconButton(onClick = controller::toggle) {
                Image(
                    imageVector = when (uiMode) {
                        UiMode.DARK -> Icons.Outlined.Lightbulb
                        UiMode.LIGHT -> Icons.Default.Lightbulb
                    },
                    contentDescription = "Toggle UI mode",
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PostListContent(
    isLoading: Boolean,
    posts: List<HomeState.Post>,
    onNavigateToDetail: (Int) -> Unit,
    onRefresh: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = onRefresh,
    )

    val listState = rememberInNavStack(
        key = "scrollState-$isLoading",
        compute = { LazyListState() },
    )

    Box(Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
        LazyColumn(Modifier.fillMaxSize(), state = listState) {
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

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

/**
 * Dummy posts for showing shimmer animation while posts are loading
 */
private val loadingPostCards = List(6) { HomeState.Post(it, "", "", "") }
