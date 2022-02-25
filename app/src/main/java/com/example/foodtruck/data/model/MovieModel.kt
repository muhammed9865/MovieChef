package com.example.foodtruck.data.model

import com.example.foodtruck.data.model.credits.Cast
import com.example.foodtruck.data.model.credits.Crew
import com.example.foodtruck.data.model.credits.MovieDetailsCredits
import com.example.foodtruck.data.model.movie.Genre
import com.example.foodtruck.data.model.movie.MovieDetails

data class MovieModel(
    val name: String = "",
    val rating: Double = 0.0,
    val overview: String = "",
    val logo_image: String = "",
    val backdrop_image: String? = null,
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    val duration: String = "",
    val release_date: String = "",
    val genres: String = ""
) {
    class Builder(
        private val movieDetails: MovieDetails,
        private val movieCredits: MovieDetailsCredits
    ) {
        fun build() = MovieModel(
            name = movieDetails.title,
            rating = movieDetails.vote_average,
            logo_image = movieDetails.poster_path,
            backdrop_image = movieDetails.backdrop_path,
            cast = movieCredits.cast,
            crew = movieCredits.crew,
            duration = getTime(movieDetails.runtime),
            release_date = movieDetails.release_date,
            genres = getGenres(movieDetails.genres),
            overview = movieDetails.overview
        )

        private fun getTime(time: Int): String {
            val hours = time / 60
            val minutes = time % 60

            return "${hours}h ${minutes}m"
        }

        private fun getGenres(genresList: List<Genre>): String {
            var genres = ""
            genresList.forEachIndexed { i, genre ->
                genres += if (i != genresList.lastIndex)
                    "${genre.name}, "
                else
                    genre.name
            }

            return genres
        }
    }
}