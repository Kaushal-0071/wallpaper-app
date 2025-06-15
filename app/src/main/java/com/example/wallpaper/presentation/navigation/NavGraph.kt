package com.example.wallpaper.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.presentation.favourites_screen.FavouritesScreen
import com.example.wallpaper.presentation.home_screen.HomeScreen
import com.example.wallpaper.presentation.search_screen.SearchScreen
import com.example.wallpaper.presentation.wallpaperScreen.WallPaperScreen


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    images:List<BackendImageDto>,
    onImageClick: (String) -> Unit,
    onFabClick: () -> Unit,
    onSearchClick: () -> Unit
){


SharedTransitionLayout {
   NavHost(navController=navController, startDestination = Routes.HOME_SCREEN) {
       composable(route = Routes.HOME_SCREEN) {
        HomeScreen(
               onImageClick = { onImageClick(it) },
               images = images,
            sharedTransitionScope = this@SharedTransitionLayout,
            animatedVisibilityScope = this,
            onFabClick=onFabClick,

            onSearchClick = onSearchClick

           )
       }
       composable(route = Routes.WALLPAPER_SCREEN + "/{uri}") {
           val uri = it.arguments?.getString("uri")




           WallPaperScreen(uri?:"no uri", animatedVisibilityScope = this)
       }

       composable(route = Routes.SEARCH_SCREEN) {
           SearchScreen(
               onImageClick = onImageClick,
               sharedTransitionScope = this@SharedTransitionLayout,
               animatedVisibilityScope = this
           )
       }
       composable(route = Routes.FAVOURITES_SCREEN) {
           FavouritesScreen(
               onImageClick = onImageClick,
               sharedTransitionScope = this@SharedTransitionLayout,
               animatedVisibilityScope = this
           )
       }
   }
}

}