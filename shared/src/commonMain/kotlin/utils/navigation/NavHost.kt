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
package utils.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.onSubscription
import utils.appfinisher.LocalAppFinisher
import utils.backhandler.BackHandler
import utils.navigation.impl.NavHostScopeImpl
import utils.navigation.impl.NavigationEvents
import utils.navigation.impl.rememberNavHostScope

/**
 * Constructs a navigation graph in this composition
 *
 * @param navigationController The navigation controller
 * @param initialState The start destination state in the navigation graph
 * @param block The lambda for constructing navigation graph
 */
@Composable
fun <STATE : Any> NavHost(
    navigationController: NavigationController<STATE>,
    initialState: STATE,
    block: NavHostScope<STATE>.() -> Unit,
) {
    val hostScope = rememberNavHostScope(navigationController, block)

    hostScope.NavHostImpl(
        navigationController = navigationController,
        initialState = initialState,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun <STATE : Any> NavHostScopeImpl<STATE>.NavHostImpl(
    navigationController: NavigationController<STATE>,
    initialState: STATE,
) {
    val state by rememberNavState(
        navigationController = navigationController,
        initialState = initialState,
    )

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            when (targetState?.animation) {
                NavigationAnimation.NAVIGATING -> {
                    slideInHorizontally { height -> height } + fadeIn() with
                        slideOutHorizontally { height -> -height } + fadeOut()
                }
                NavigationAnimation.POPPING_UP -> {
                    slideInHorizontally { height -> -height } + fadeIn() with
                        slideOutHorizontally { height -> height } + fadeOut()
                }
                else -> {
                    fadeIn() with fadeOut()
                }
            }.using(SizeTransform(clip = false))
        },
    ) { nextState ->
        val navState = nextState?.state
        if (navState != null) {
            this@NavHostImpl.compose(navState)
        }
    }

    BackHandler(true) { navigationController.navigateUp() }

    val appFinisher = LocalAppFinisher.current

    LaunchedEffect(state) {
        if (state?.animation === NavigationAnimation.EXIT) {
            appFinisher.finish()
        }
    }
}

@Composable
private fun <STATE : Any> rememberNavState(
    navigationController: NavigationController<STATE>,
    initialState: STATE,
): State<NavState<STATE>?> {
    val initialValue = navigationController.currentState?.let {
        NavState(it, NavigationAnimation.NO_ANIMATION)
    }

    return produceState<NavState<STATE>?>(
        key1 = navigationController,
        key2 = initialState,
        initialValue = initialValue,
    ) {
        navigationController.events
            .onSubscription {
                if (navigationController.backStackSize == 0) {
                    navigationController.navigateTo(initialState)
                }
            }.collect { event ->
                val animation = when (event) {
                    is NavigationEvents.InitialState -> NavigationAnimation.NO_ANIMATION
                    is NavigationEvents.OnNavigateTo -> NavigationAnimation.NAVIGATING
                    is NavigationEvents.OnPopUp -> NavigationAnimation.POPPING_UP
                    is NavigationEvents.OnStackEmpty -> NavigationAnimation.EXIT
                }
                value = NavState(event.state, animation)
            }
    }
}
