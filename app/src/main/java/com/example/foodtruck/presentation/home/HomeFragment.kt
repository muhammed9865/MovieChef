package com.example.foodtruck.presentation.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import com.example.foodtruck.R
import com.example.foodtruck.data.model.trending.Result
import com.example.foodtruck.data.repository.MoviesRepository
import com.example.foodtruck.databinding.FragmentHomeBinding
import com.example.foodtruck.presentation.details.DetailsFragment
import com.example.foodtruck.presentation.home.adapter.HomeAdapter
import com.example.foodtruck.presentation.home.adapter.TransferMovie
import com.example.foodtruck.presentation.home.viewmodel.HomeViewModel
import com.example.foodtruck.presentation.home.viewmodel.HomeViewModelFactory
import com.example.foodtruck.presentation.util.DetailsTransition
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), TransferMovie {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(MoviesRepository.getInstance())
    }
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val trendingAdapter: HomeAdapter by lazy {
        HomeAdapter(false)
    }
    private val exploreAdapter: HomeAdapter by lazy {
        HomeAdapter(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        render()
        prepareLists()
        onOptionSelected()
        onPageChanged()

        lifecycleScope.launch {
            viewModel.intentChannel.send(HomeIntents.GetTrendingMovies)
        }
        lifecycleScope.launch {
            viewModel.intentChannel.send(HomeIntents.Explore)
        }

        exploreAdapter.setOnTransferMovie(this)
        trendingAdapter.setOnTransferMovie(this)

        return binding.root
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.homeStates.collect { state ->
                when (state) {
                    is HomeViewStates.Idle -> {
                    }
                    is HomeViewStates.Loading -> {
                        loadingData()
                    }
                    is HomeViewStates.ShowTrending -> {
                        binding.trendingPb.visibility = View.GONE
                        trendingAdapter.submitList(state.movies.results.shuffled())
                    }
                    is HomeViewStates.ShowExplore -> {
                        binding.exploreMoviesPb.visibility = View.GONE
                        exploreAdapter.submitList(state.movies.shuffled())
                        binding.explorePageNum.text = viewModel.pageNumber.toString()
                    }
                    is HomeViewStates.Error -> {
                        Log.d(TAG, "render: ${state.message}")
                    }
                }
            }
        }
    }

    private fun loadingData() {
        binding.trendingPb.visibility = View.VISIBLE
        binding.exploreMoviesPb.visibility = View.VISIBLE
    }

    private fun onOptionSelected() {
        binding.cbAdult.setOnCheckedChangeListener { _, _ ->
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(
                    HomeIntents.ChangeAdultState
                )
            }
        }

        binding.cbTop.setOnCheckedChangeListener { _, _ ->
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(
                    HomeIntents.ShowTopRated
                )
            }
        }
    }

    private fun onPageChanged() {
        binding.apply {
            explorePageUp.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    viewModel.intentChannel.send(HomeIntents.PageUp)
                }
            }

            explorePageDown.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    viewModel.intentChannel.send(HomeIntents.PageDown)
                }
            }
        }
    }



    private fun prepareLists() {
        binding.trendingMoviesRb.adapter = trendingAdapter
        binding.exploreMoviesRv.adapter = exploreAdapter
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onMovieTransferred(movie_image: ImageView, movie: Result) {

    }
}