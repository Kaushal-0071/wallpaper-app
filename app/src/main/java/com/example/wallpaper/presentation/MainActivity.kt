package com.example.wallpaper.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.wallpaper.presentation.home_screen.HomeViewModel
import com.example.wallpaper.presentation.navigation.NavGraphSetup
import com.example.wallpaper.presentation.navigation.Routes
import com.example.wallpaper.presentation.theme.WallpaperTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WallpaperTheme {
                val viewModel: HomeViewModel = hiltViewModel()
                val navController = rememberNavController()
                NavGraphSetup(
                    navController = navController, viewModel.images, onImageClick = { it ->
                        val encodedUrl = URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
                        navController.navigate(Routes.WALLPAPER_SCREEN + "/$encodedUrl")
                    },
                    onFabClick = {
                        navController.navigate(Routes.FAVOURITES_SCREEN)

                    },
                    onSearchClick = {
                        navController.navigate(Routes.SEARCH_SCREEN)

                    })

            }
        }
    }
}
