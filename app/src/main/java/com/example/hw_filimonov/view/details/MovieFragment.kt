package com.example.hw_filimonov.view.details

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hw_filimonov.MovieDTO
import com.example.hw_filimonov.R
import com.example.hw_filimonov.databinding.FragmentMovieBinding
import com.example.hw_filimonov.model.Movie
import com.example.hw_filimonov.viewmodel.AppState
import com.example.hw_filimonov.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie.view.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val YOUR_API_KEY = "..."

class MovieFragment : Fragment() {


    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Movie()
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        loadMovie()
    }



    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                binding.loadingLayout.visibility = View.GONE
                setData(movieData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.reload)) { viewModel.getMovieFromLocalSource() }
                    .show()
            }
        }
    }

    private fun setData(movieData: Movie) {
        binding.movieName.text = movieData.movieName.movieName
        binding.movieYear.text = String.format(
            getString(R.string.movie_string),
            movieData.movieName.year.toString()
        )
        binding.ratingValue.text = movieData.rating.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(bundle: Bundle) = MovieFragment()
    }


    private fun displayMovie(movieDTO: MovieDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            val movieName = movieBundle.movieName
            movieName.text = movieName.movieName
            movieYear.text = String.format(
                getString(R.string.new_movies),
            )
            ratingValue.text = movieDTO.fact?.rating.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie() {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?rating=${movieBundle.movieName.ratingValue}")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        YOUR_API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val movieDTO: MovieDTO =
                        Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)
                    handler.post { displayMovie(movieDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }



}

