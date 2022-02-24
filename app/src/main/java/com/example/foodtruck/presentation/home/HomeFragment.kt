package com.example.foodtruck.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodtruck.R
import com.example.foodtruck.data.repository.MoviesRepository
import com.example.foodtruck.databinding.FragmentHomeBinding
import com.example.foodtruck.presentation.home.adapter.HomeAdapter
import com.example.foodtruck.presentation.home.viewmodel.HomeViewModel
import com.example.foodtruck.presentation.home.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(MoviesRepository.getInstance())
    }
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val adapter: HomeAdapter by lazy {
        HomeAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        render()
        prepareList()

        lifecycleScope.launch {
            viewModel.intentChannel.send(HomeIntents.GetTrendingMovies)
        }
        return binding.root
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.homeStates.collect { state ->
                when (state) {
                    is HomeViewStates.Loading -> {binding.trendingPb.visibility = View.VISIBLE}
                    is HomeViewStates.ShowTrending -> {
                        binding.trendingPb.visibility = View.GONE
                        Log.d(TAG, "render: ${state.movies.toString()}")
                        adapter.submitList(state.movies.results.shuffled())
                    }
                    is HomeViewStates.Error -> {
                        Log.d(TAG, "render: ${state.message}")
                    }
                }
            }
        }
    }


    private fun prepareList() {
        binding.trendingMoviesRb.adapter = adapter
    }
    companion object {
        private const val TAG = "HomeFragment"
    }
}