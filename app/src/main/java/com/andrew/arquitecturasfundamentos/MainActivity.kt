package com.andrew.arquitecturasfundamentos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.andrew.arquitecturasfundamentos.data.MoviesRepository
import com.andrew.arquitecturasfundamentos.data.local.LocalDataSource
import com.andrew.arquitecturasfundamentos.data.local.MoviesDatabase
import com.andrew.arquitecturasfundamentos.data.remote.RemoteDataSource
import com.andrew.arquitecturasfundamentos.ui.screens.Home

class MainActivity : ComponentActivity() {

    lateinit var db: MoviesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java,
            "movie_db"
        ).build()
        val repository = MoviesRepository(
            localDataSource = LocalDataSource(db.moviesDao()),
            remoteDataSource = RemoteDataSource()
        )
        setContent {
            Home(repository)
        }
    }
}