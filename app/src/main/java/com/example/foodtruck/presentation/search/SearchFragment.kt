package com.example.foodtruck.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodtruck.R
import com.example.foodtruck.databinding.FragmentSearchBinding
import com.example.foodtruck.presentation.home.HomeIntents
import com.example.foodtruck.presentation.home.adapter.HomeAdapter
import com.example.foodtruck.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val searchAdapter: HomeAdapter by lazy {
        HomeAdapter(true)
    }
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        render()
        onOptionSelected()

        binding.searchView.setOnQueryTextListener(this)
        binding.searchRv.adapter = searchAdapter


        return binding.root
    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewStates.collect { state ->
                when (state) {
                    is SearchViewStates.Idle -> {binding.searchPb.visibility = View.GONE}
                    is SearchViewStates.Searching -> binding.searchPb.visibility = View.VISIBLE
                    is SearchViewStates.SearchResult -> {
                        binding.searchPb.visibility = View.GONE
                        searchAdapter.submitList(state.movies)
                    }
                    is SearchViewStates.Error -> {
                        Log.d(TAG, "render: error :: ${state.message}")
                    }
                }
            }
        }
    }

    private fun onOptionSelected() {
        binding.cbAdult.setOnCheckedChangeListener { _, _ ->
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(
                    SearchIntents.ChangeAdultState
                )
            }
        }

        binding.cbTop.setOnCheckedChangeListener { _, _ ->
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(
                    SearchIntents.ShowTopRated
                )
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(SearchIntents.Search(query))

            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let {
            lifecycleScope.launchWhenStarted {
                viewModel.intentChannel.send(SearchIntents.Search(query))
            }
        }
        return true
    }



    companion object {
        private const val TAG = "SearchFragment"
    }

}