package com.example.foodtruck.presentation.details

import android.graphics.Color
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.bumptech.glide.Glide
import com.example.foodtruck.Constants
import com.example.foodtruck.R
import com.example.foodtruck.data.model.MovieModel
import com.example.foodtruck.databinding.FragmentDetailsBinding
import com.example.foodtruck.presentation.MainViewModel
import com.example.foodtruck.presentation.details.adapter.DetailsAdapter
import com.example.foodtruck.presentation.details.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.floor


class DetailsFragment : Fragment() {
    private val binding: FragmentDetailsBinding by lazy {
        FragmentDetailsBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val viewModel: DetailsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val crewAdapter: DetailsAdapter by lazy {
        DetailsAdapter(false)
    }
    private val castAdapter: DetailsAdapter by lazy {
        DetailsAdapter(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        render()
        binding.detailsCrewRv.adapter = crewAdapter
        binding.detailsCastRv.adapter = castAdapter
        lifecycleScope.launch {
            viewModel.intentChannel.send(DetailsIntents.GetMovie(mainViewModel.movieId))
        }

        binding.navigateUp.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlSystemBars(hide = true)
    }

    override fun onDetach() {
        super.onDetach()
        controlSystemBars(hide = false)
    }



    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.states.collect { state ->
                when(state) {
                    is DetailsViewStates.Idle -> {}
                    is DetailsViewStates.Loading -> isLoading(true)
                    is DetailsViewStates.ShowMovieDetails -> {
                        isLoading(false)
                        loadImage(state.movie.logo_image, binding.detailsMovieImage)
                        loadImage(state.movie.backdrop_image, binding.detailsMoviePoster)
                        loadMovieDetails(state.movie)
                        crewAdapter.submitList(state.movie.crew)
                        castAdapter.submitList(state.movie.cast)
                    }
                    is DetailsViewStates.Error -> {
                        Log.d("Details", "render: ${state.message}")
                        Snackbar
                            .make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.RED)
                            .show()
                    }
                }
            }
        }
    }

    private fun loadImage(url: String?, view: ImageView) {
        Glide.with(requireContext())
            .load(Constants.TMBD_IMAGE_PATH + url)
            .placeholder(requireContext().getDrawable(R.drawable.ic_baseline_image_24))
            .into(view)
            .onLoadFailed(requireContext().getDrawable(R.drawable.ic_baseline_image_24))
    }

    private fun loadMovieDetails(movie: MovieModel) {
        binding.apply {
            detailsMovieOverview.text = movie.overview
            detailsMovieDuration.text = movie.duration
            detailsMovieGenres.text = movie.genres
            detailsMovieName.text = movie.name
            detailsMovieRatePb.progress = floor(movie.rating).toInt()
            detailsMovieRateText.text = movie.rating.toString()
            detailsMovieDate.text = movie.release_date
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                imgPb.visibility = View.VISIBLE
                crewPb.visibility = View.VISIBLE
                castPb.visibility = View.VISIBLE
            }else {
                imgPb.visibility = View.GONE
                crewPb.visibility = View.GONE
                castPb.visibility = View.GONE
            }
        }
    }

    private fun controlSystemBars(hide: Boolean) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(requireActivity().window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        if (hide) {
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        }else windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }


}