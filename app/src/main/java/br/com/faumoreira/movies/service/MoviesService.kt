package br.com.faumoreira.movies.service

import br.com.faumoreira.movies.data.web.FilmResult
import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

interface MoviesService {

    @GET("movie/upcoming")
    fun listMovies(@QueryMap queryMap : Map<String, String>): Observable<FilmResult>
}