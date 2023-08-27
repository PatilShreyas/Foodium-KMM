package di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.shreyaspatil.foodium.db.FoodiumDb

actual class SqlDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(FoodiumDb.Schema, context, "foodium.db")
    }
}