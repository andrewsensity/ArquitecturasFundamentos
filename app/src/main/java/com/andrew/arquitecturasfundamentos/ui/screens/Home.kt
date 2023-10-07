package com.andrew.arquitecturasfundamentos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.andrew.arquitecturasfundamentos.data.MoviesRepository
import com.andrew.arquitecturasfundamentos.data.local.MoviesDao
import com.andrew.arquitecturasfundamentos.data.model.Movie
import com.andrew.arquitecturasfundamentos.ui.theme.ArquitecturasFundamentosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(moviesRepository: MoviesRepository) {
    ArquitecturasFundamentosTheme {
        val viewModel = viewModel { HomeViewModel(moviesRepository) }
//                val state by viewModel.state.observeAsState(MainViewModel.UiState()) //LiveData
//                val state by viewModel.state.collectAsState() //StateFlow
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(text = "Movies") })
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(it),
                        columns = GridCells.Adaptive(120.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(viewModel.state.movies) { movie ->
                            Card(
                                onClick = { },
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dp
                                )
                            ) {
                                MovieItem(movie = movie) {
                                    viewModel.onMovieClick(movie = movie)
                                }
                            }
                        }
                    }
                    if (viewModel.state.loading) CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier.clickable { onClick() }
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f),
                model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
                contentDescription = movie.title
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = movie.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (movie.favorite) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                imageVector = Icons.Default.Favorite,
                contentDescription = movie.title,
                tint = Color.Red
            )
        }
    }
}