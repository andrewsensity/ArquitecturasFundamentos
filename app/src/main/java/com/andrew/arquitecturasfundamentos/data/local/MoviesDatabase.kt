package com.andrew.arquitecturasfundamentos.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.andrew.arquitecturasfundamentos.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Database(entities = [LocalMovie::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<LocalMovie>)
    @Query("SELECT * FROM LocalMovie")
    fun getMovie(): Flow<List<LocalMovie>>
    @Update
    suspend fun updateMovie(movie: LocalMovie)
    @Query("SELECT COUNT(*) FROM LocalMovie")
    suspend fun count(): Int
}

@Entity
data class LocalMovie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favorite: Boolean = false
)

fun LocalMovie.toMovie() = Movie(
    id = id, title = title, overview = overview, posterPath = posterPath, favorite = favorite
)
fun Movie.toLocalMovie() = LocalMovie(
    id = id, title = title, overview = overview, posterPath = posterPath, favorite = favorite
)