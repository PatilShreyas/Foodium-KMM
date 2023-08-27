package di.component

import android.app.Application
import di.SqlDriverFactory

actual typealias AppContext = Application

actual fun provideDriverFactory(appContext: AppContext): SqlDriverFactory =
    SqlDriverFactory(appContext)
