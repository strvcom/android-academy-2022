package com.strv.movies.network

import androidx.room.withTransaction
import com.strv.movies.data.dao.MoviesDao
import com.strv.movies.data.mapper.MovieDetailMapper
import com.strv.movies.data.mapper.MovieMapper
import com.strv.movies.data.mapper.toDomain
import com.strv.movies.data.mapper.toEntity
import com.strv.movies.database.MoviesDatabase
import com.strv.movies.extension.Either
import com.strv.movies.model.Movie
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.MovieDetailDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val moviesDao: MoviesDao,
    private val movieDetailMapper: MovieDetailMapper,
    private val movieMapper: MovieMapper,
    private val moviesDatabase: MoviesDatabase
) {

    suspend fun getMovieDetail(movieId: Int): Either<String, MovieDetail> {
        return try {
            val movie = api.getMovieDetail(movieId)
            storeMovieDetail(movie)
            Either.Value(movieDetailMapper.map(movie))
        } catch (exception: Throwable) {
            Either.Error(exception.localizedMessage ?: "Network error")
        }
    }

    suspend fun getPopularMovies(): Either<String, List<Movie>> {
        return try {
            val popularMovies = api.getPopularMovies()
            Either.Value(popularMovies.results.map { movieMapper.map(it) })
        } catch (exception: Throwable) {
            Either.Error(exception.localizedMessage ?: "Network error")
        }
    }

    fun observeMovieDetail(movieId: Int): Flow<MovieDetail?> =
        moviesDao.observeMovieDetail(movieId).map {
            it?.toDomain()
        }

    private suspend fun storeMovieDetail(movie: MovieDetailDTO) {
        moviesDatabase.withTransaction {
            moviesDao.insertMovieDetail(movie.toEntity())
            moviesDao.insertGenres(movie.genres.map { it.toEntity() })
            moviesDao.insertMovieGenres(movie.genres.map { it.toEntity(movie.id) })
        }
    }
}
