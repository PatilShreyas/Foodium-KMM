import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ui.HomeScreen

@Composable
fun App() {
    MaterialTheme(lightColors(primary = Color.Black)) {
        HomeScreen()
    }
}