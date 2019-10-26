package br.com.faumoreira.movies.data.web

import com.google.gson.annotations.SerializedName

data class Film (
    @SerializedName("poster_path")
    val posterPath : String,
    @SerializedName("release_date")
    val date : String,
    @SerializedName("original_title")
    val originalTitle : String,
    @SerializedName("title")
    val title : String)