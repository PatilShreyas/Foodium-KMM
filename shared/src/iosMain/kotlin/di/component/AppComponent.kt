package di.component

import di.SqlDriverFactory

/**
 * Just declaration since no use at all
 */
actual class AppContext

actual fun provideDriverFactory(appContext: AppContext): SqlDriverFactory = SqlDriverFactory()