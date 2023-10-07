package com.andrew.arquitecturasfundamentos.data

import com.andrew.arquitecturasfundamentos.data.local.LocalDataSource
import com.andrew.arquitecturasfundamentos.data.model.Movie
import com.andrew.arquitecturasfundamentos.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    val movies: Flow<List<Movie>> = localDataSource.movies

    suspend fun updateMovie(movie: Movie) = localDataSource.updateMovie(movie)

    suspend fun requestMovies() {
        if(localDataSource.count() == 0) {
            localDataSource.insertMovies(remoteDataSource.getMovies())
        }
    }
}