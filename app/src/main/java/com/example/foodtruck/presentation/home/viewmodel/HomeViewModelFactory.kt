package com.example.foodtruck.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodtruck.data.repository.MoviesRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repo: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}