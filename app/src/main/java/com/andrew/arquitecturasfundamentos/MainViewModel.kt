package com.andrew.arquitecturasfundamentos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    var state by mutableStateOf(UiState())
        private set // Compose
    /*private val _state = MutableStateFlow(UiState())
   val state: StateFlow<UiState> = _state //StateFlow*/
    /*private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state //LiveData*/

    init {
        viewModelScope.launch {
            state.loading = true
            delay(2000)
            state = UiState(
                movies = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MoviesService::class.java)
                    .getMovies()
                    .results,
                loading = false
            )
        }
    }

    fun onMovieClick(movie: ServerMovie) {
        val movies = state.movies.toMutableList()
        movies.replaceAll {
            if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else it
        }
        state = state.copy(movies = movies)
    }

    data class UiState(
        var loading: Boolean = false,
        val movies: List<ServerMovie> = emptyList()
    )
}