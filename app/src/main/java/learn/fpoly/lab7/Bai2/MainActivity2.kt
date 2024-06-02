package learn.fpoly.lab7.Bai2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import learn.fpoly.lab7.Bai2.Screen
import learn.fpoly.lab7.Bai2.ScreenNavigation

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ScreenNavigation(navController)
        }
    }
}
