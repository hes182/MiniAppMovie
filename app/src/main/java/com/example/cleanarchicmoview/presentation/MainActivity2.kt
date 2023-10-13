package com.example.cleanarchicmoview.presentation

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.runTimeToReadableDuration
import com.example.cleanarchicmoview.common.extension.toFullImageLink
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.databinding.ActivityMain2Binding
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.presentation.home.FHomeFragment
import com.example.cleanarchicmoview.presentation.movie_detail.MovieDetailViewModel
import com.example.cleanarchicmoview.presentation.moviefavorite.FavoriteMivieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val naview: BottomNavigationView = findViewById(R.id.nav_view)
        naview.setupWithNavController(navController)
    }

}