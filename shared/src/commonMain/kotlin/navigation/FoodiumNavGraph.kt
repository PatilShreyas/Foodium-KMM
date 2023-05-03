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
package navigation

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
import androidx.compose.runtime.Composable
import di.component.AppComponent
import ui.screen.detail.PostDetailScreen
import ui.screen.home.HomeScreen
import utils.navigation.NavHost
import utils.navigation.OnState
import utils.navigation.navStackViewModel
import utils.navigation.rememberNavigationController

@Composable
fun FoodiumNavGraph() {
    val navController = rememberFoodiumScreenNavController()

    NavHost(navController, initialState = Screen.Home) {
        OnState<Screen.Home> {
            val viewModel = it.navStackViewModel {
                AppComponent.viewModelComponent.provideHomeViewModel(it)
            }

            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { postId -> navController.navigateTo(Screen.PostDetail(postId)) },
            )
        }

        OnState<Screen.PostDetail> {
            val postId = it.state.postId
            val viewModel = it.navStackViewModel(key = postId) {
                AppComponent.viewModelComponent.providePostDetailViewModel(it, postId)
            }

            PostDetailScreen(viewModel = viewModel, onNavigateUp = navController::navigateUp)
        }
    }
}

@Composable
fun rememberFoodiumScreenNavController() = rememberNavigationController<Screen, Map<String, String>>(
    onSave = { screen -> screen.asSavable() },
    onRestore = { savable -> buildScreenFromSavable(savable) },
)
