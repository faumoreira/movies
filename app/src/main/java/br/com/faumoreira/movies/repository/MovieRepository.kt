package br.com.faumoreira.movies.repository

import br.com.faumoreira.movies.service.MoviesService
import br.com.faumoreira.movies.data.Movie
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.util.*


class MovieRepository {
    val language: String
    val service: MoviesService
    private val BASE_IMAGES_URL = "https://image.tmdb.org/t/p"

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        service = retrofit.create<MoviesService>(MoviesService::class.java)
        val locale = Locale.getDefault()
        language = locale.language + "-" + locale.country
    }
    fun loadMovies(): Observable<Movie>? {
        return service.listMovies(createQueryMap())
            .flatMap { filmResults -> Observable.from(filmResults.results) }
            .map { film ->
                Movie(
                    film.title,
                    "${film.originalTitle}, ${film.date}",
                    "${BASE_IMAGES_URL}/w300${film.posterPath}"
                )
            }
    }

    private fun createQueryMap(): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap.put("api_key", "1f54bd990f1cdfb230adb312546d765d")
        queryMap.put("language", language)
        return queryMap
    }

}