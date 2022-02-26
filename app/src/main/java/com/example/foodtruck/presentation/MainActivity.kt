package com.example.foodtruck.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodtruck.R
import com.example.foodtruck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }
    private val viewModel: MainViewModel by viewModels()

    private val navController: NavController by lazy {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navHostFragment.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        NavigationUI.setupWithNavController(binding.botNav, navController)
        navController.addOnDestinationChangedListener(this)


        binding.botNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.menu_library -> {
                    navController.navigate(R.id.libraryFragment)
                    true
                }
                R.id.menu_search -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                else -> false
            }
        }

    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.detailsFragment -> showBottomNav(false)
            else -> showBottomNav(true)
        }
    }


    private fun showBottomNav(show: Boolean) {
        binding.botNav.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
            override fun onViewAttachedToWindow(p0: View?) {
              //  p0?.animate()?.translationYBy(50F)?.duration = 500
            }

            override fun onViewDetachedFromWindow(p0: View?) {
                p0?.animate()?.translationYBy(-50F)?.duration = 500
            }
        })
        binding.botNav.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}