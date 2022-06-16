package com.strv.movies.network

import androidx.room.withTransaction
import com.strv.movies.data.dao.MoviesDao
import com.strv.movies.data.entity.toDomain
import com.strv.movies.database.MoviesDatabase
import com.strv.movies.extension.Either
import com.strv.movies.model.Movie
import com.strv.movies.model.MovieDetail
import com.strv.movies.model.MovieDetailDTO
import com.strv.movies.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val moviesDao: MoviesDao,
    private val moviesDatabase: MoviesDatabase
) {

    suspend fun fetchMovieDetail(movieId: Int): Either<String, String> {
        return try {
            val movie = api.getMovieDetail(movieId)
            storeMovieDetail(movie)
            Either.Value(movie.title)
        } catch (exception: Throwable) {
            Either.Error(exception.localizedMessage ?: "Network error")
        }
    }

    suspend fun fetchPopularMovies(fetchFromRemote: Boolean): Flow<Either<String, List<Movie>>> =
        flow {
            val localData = moviesDao.observePopularMovies().map { it.toDomain() }
            emit(Either.Value(localData))
            val isDbEmpty = moviesDao.observePopularMovies().isEmpty()
            val shouldLoadFromCache = !isDbEmpty && fetchFromRemote
            if (shouldLoadFromCache) {
                return@flow
            }
            try {
                val response = api.getPopularMovies().results.map { it.toEntity() }

                //could delete database here to make sure the list wont get too long
                //but it would be a bit heavier

                moviesDao.insertPopularMovies(response)
                val cachedMovies = moviesDao.observePopularMovies()
                    .map { it.toDomain() }
                    .sortedByDescending { it.popularity }
                emit(Either.Value(cachedMovies))
            } catch (t: Throwable) {
                emit(Either.Error(t.localizedMessage ?: "Network Error"))
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
