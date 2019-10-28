package br.com.faumoreira.movies.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.faumoreira.movies.data.Movie
import br.com.faumoreira.movies.repository.MovieRepository
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.random.Random

class MainViewModel( application: Application
) : AndroidViewModel(application) {

    val movieRepository = MovieRepository()
    val movies : ArrayList<Movie> = ArrayList()
    var movie1 : MutableLiveData<Movie> = MutableLiveData()
    var movie2 : MutableLiveData<Movie> = MutableLiveData()
    var movie3 : MutableLiveData<Movie> = MutableLiveData()
    var error : MutableLiveData<String> = MutableLiveData()

    fun loadMovies(){
        movieRepository.listMovies()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({ movie ->
                movies.add(movie)
            }, { e ->
                Log.e("teste","",e)
                error.value = "Erro na busca de filmes: ${e.message}"
            },{
                error.value = null
                refresh()
            })
    }

    fun refresh(){
        getNewMovie(movie1)
        getNewMovie(movie2)
        getNewMovie(movie3)
    }

    fun changeMovie1(){
        getNewMovie(movie1)
    }
    fun changeMovie2(){
        getNewMovie(movie2)
    }
    fun changeMovie3(){
        getNewMovie(movie3)
    }

    fun getNewMovie(movie: MutableLiveData<Movie>) {
        movie.value = movies.get(Random.nextInt(movies.size-1))
    }
}