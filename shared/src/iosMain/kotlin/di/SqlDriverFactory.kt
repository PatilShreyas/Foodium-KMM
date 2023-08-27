package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.shreyaspatil.foodium.db.FoodiumDb

actual class SqlDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(FoodiumDb.Schema, "foodium.db")
    }
}