package br.com.faumoreira.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.faumoreira.movies.R
import br.com.faumoreira.movies.repository.MovieRepository
import br.com.faumoreira.movies.viewModel.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btRefresh.setOnClickListener {
            viewModel.refresh()
        }
        btRemove1.setOnClickListener{
            viewModel.changeMovie1()
        }
        btRemove2.setOnClickListener{
            viewModel.changeMovie2()
        }
        btRemove3.setOnClickListener{
            viewModel.changeMovie3()
        }

        bindMovies()

        viewModel.loadMovies()
    }

    fun bindMovies(){
        viewModel.movie1.observe(this, Observer {
            txtTitle1.text = it.title
            txtOriginalTitle1.text = it.originalTitle
            Glide.with(this)
                .load(it.imgUrl)
                .into(poster1)
        })

        viewModel.movie2.observe(this, Observer {
            txtTitle2.text = it.title
            txtOriginalTitle2.text = it.originalTitle
            Glide.with(this)
                .load(it.imgUrl)
                .into(poster2)
        })

        viewModel.movie3.observe(this, Observer {
            txtTitle3.text = it.title
            txtOriginalTitle3.text = it.originalTitle
            Glide.with(this)
                .load(it.imgUrl)
                .into(poster3)
        })
    }
}
