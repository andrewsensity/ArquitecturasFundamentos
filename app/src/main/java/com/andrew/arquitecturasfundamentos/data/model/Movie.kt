package com.andrew.arquitecturasfundamentos.data.model

import com.andrew.arquitecturasfundamentos.data.remote.ServerMovie

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favorite: Boolean = false
)