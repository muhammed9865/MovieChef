package com.example.foodtruck.data.model.movie

data class MovieDetails(
    val adult: Boolean, // false
    val backdrop_path: String?, // null
    val belongs_to_collection: Any?, // null
    val budget: Int, // 0
    val genres: List<Genre>,
    val homepage: String,
    val id: Int, // 231565
    val imdb_id: String, // tt3078718
    val original_language: String, // it
    val original_title: String, // Zoran, il mio nipote scemo
    val overview: String, // Paolo, 40 years old, lives in a small Friulian town close to the north-eastern border. Unreliable and with a passion for good wine, he spends his days at the local tavern and stubbornly stalks his ex-wife.
    val popularity: Double, // 2.185
    val poster_path: String, // /lvVHTqEtz4bHOl8rf10MbN9CkDf.jpg
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String, // 2013-10-31
    val revenue: Int, // 0
    val runtime: Int, // 112
    val spoken_languages: List<SpokenLanguage>,
    val status: String, // Released
    val tagline: String,
    val title: String, // Zoran, My Nephew the Idiot
    val video: Boolean, // false
    val vote_average: Double, // 6.3
    val vote_count: Int // 38
)