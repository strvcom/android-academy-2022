package com.strv.movies.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.strv.movies.data.entity.GenreEntity
import com.strv.movies.data.entity.MovieDetailEntity
import com.strv.movies.data.entity.MovieGenreEntity
import com.strv.movies.data.entity.MovieDetailWithGenres
import com.strv.movies.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(entity: MovieDetailEntity)

    @Query("SELECT * FROM movie_detail WHERE movie_id = :id")
    fun observeMovieDetail(id: Int): Flow<MovieDetailWithGenres?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(enities: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(enities: List<MovieGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    suspend fun observePopularMovies(): List<MovieEntity>

    @Query("DELETE FROM movie")
    suspend fun deleteMovies()

}
