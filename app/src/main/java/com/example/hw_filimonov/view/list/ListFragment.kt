package com.example.hw_filimonov.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hw_filimonov.R
import com.example.hw_filimonov.databinding.FragmentListBinding
import com.example.hw_filimonov.model.Movie
import com.example.hw_filimonov.view.details.MovieFragment
import com.example.hw_filimonov.viewmodel.AppState
import com.example.hw_filimonov.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val adapter = ListFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                manager.beginTransaction()
                    .add(R.id.container, MovieFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })
    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listFragmentRecyclerView.adapter = adapter
        binding.listFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getMovieFromLocalSourceWorld()
            binding.listFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getMovieFromLocalSourceRus()
            binding.listFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                adapter.setMovie(appState.movieData)
            }
            is AppState.Loading -> {
                binding.listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.listFragmentFAB,
                        getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) { viewModel.getMovieFromLocalSourceRus() }
                    .show()
            }
        }
    }

    companion object {
        fun newInstance() =
            ListFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }
}