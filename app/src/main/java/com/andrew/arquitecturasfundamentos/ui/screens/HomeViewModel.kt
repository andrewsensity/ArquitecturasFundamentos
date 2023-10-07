package com.andrew.arquitecturasfundamentos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrew.arquitecturasfundamentos.data.MoviesRepository
import com.andrew.arquitecturasfundamentos.data.model.Movie
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {
    var state by mutableStateOf(UiState())
        private set // Compose
    /*private val _state = MutableStateFlow(UiState())
   val state: StateFlow<UiState> = _state //StateFlow*/
    /*private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state //LiveData*/

    init {
        viewModelScope.launch {
            state.loading = true
            repository.requestMovies()

            repository.movies.collect { movieList ->
                state = UiState(movies = movieList)
            }
        }
    }

    fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie = movie.copy(favorite = !movie.favorite))
        }
    }

    data class UiState(
        var loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}