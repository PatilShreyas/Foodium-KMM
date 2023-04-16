package di.component

import kotlinx.coroutines.CoroutineScope
import ui.HomeViewModel

interface ViewModelComponent {
    fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel
}

internal class DefaultViewModelComponent(
    private val appComponent: DefaultAppComponent
) : ViewModelComponent {
    override fun provideHomeViewModel(coroutineScope: CoroutineScope): HomeViewModel {
        return HomeViewModel(coroutineScope, appComponent.postRepository)
    }
}