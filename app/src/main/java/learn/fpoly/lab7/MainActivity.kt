package learn.fpoly.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController // Thêm dòng này để sử dụng NavController trong Composable
import learn.fpoly.lab7.Bai2.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val moviesState by mainViewModel.movies.observeAsState(initial = emptyList()) // Sử dụng 'by' để nhận giá trị mới của moviesState khi nó thay đổi
            val navController = rememberNavController() // Sử dụng rememberNavController để lưu NavController giữa các recomposition
            MovieScreen(mv = moviesState, navController = navController)
        }
    }
}

@Composable
fun MovieScreen(mv: List<Movie>, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .clickable { navController.navigate(Screen.SCREEN1.route) },
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(mv) { movie ->
                Text(text = movie.title)
            }
        }
    }
}
