package com.andrew.arquitecturasfundamentos.data.local

import com.andrew.arquitecturasfundamentos.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dao: MoviesDao) {

    val movies: Flow<List<Movie>> = dao.getMovie().map { listMovies ->
        listMovies.map { localMovie ->
            localMovie.toMovie()
        }
    }

    suspend fun insertMovies(movies: List<Movie>) {
        dao.insertMovies(movie = movies.map { it.toLocalMovie() })
    }
    suspend fun updateMovie(movie: Movie) {
        dao.updateMovie(movie = movie.toLocalMovie())
    }
    suspend fun count(): Int = dao.count()
}