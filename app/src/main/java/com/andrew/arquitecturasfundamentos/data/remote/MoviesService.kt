package com.andrew.arquitecturasfundamentos.data.remote

import retrofit2.http.GET

interface MoviesService {
    @GET("discover/movie?api_key=bd0daf9eefbbeb411f66b48908007441")
    suspend fun getMovies(): MovieResult
}