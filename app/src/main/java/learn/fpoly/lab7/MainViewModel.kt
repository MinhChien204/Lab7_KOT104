package learn.fpoly.lab7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Movie(val id: Int, val title: String, val genre: String)

class MainViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    init {
        _movies.value = getSampleMovies()
    }

    private fun getSampleMovies(): List<Movie> {
        return listOf(
            Movie(1, "Movie 1", "Genre 1"),
            Movie(2, "Movie 2", "Genre 2"),
            Movie(3, "Movie 3", "Genre 3")
        )
    }
}
