package com.example.wallpaper.presentation.favourites_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wallpaper.R
import com.example.wallpaper.presentation.components.ImagesVerticalGrid
import com.example.wallpaper.presentation.theme.Background
import com.example.wallpaper.presentation.theme.accent

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun FavouritesScreen(
    onImageClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,

){
    val state = rememberPullToRefreshState()
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.empty)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    val viewModel: favouriteViewmodel = hiltViewModel()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        var refreshing:Boolean by remember { mutableStateOf(false) }
        Scaffold (
            containerColor = Background,
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Text("Favourites")
                },
                    scrollBehavior=scrollBehavior,
                    colors=TopAppBarDefaults.topAppBarColors(
                        containerColor = Background,
                        scrolledContainerColor = Background
                    )

                )

            },
            modifier = Modifier

        ){ innerPadding ->
            PullToRefreshBox(
                state = state,
                isRefreshing = refreshing,
                onRefresh =   {
                    refreshing = true
                    viewModel.refreshImages {
                        refreshing = false
                    }

                },
                modifier =  Modifier.fillMaxSize().padding(innerPadding),
                indicator = {
                   Indicator(
                       state = state,
                       modifier = Modifier.align(Alignment.TopCenter),
                       isRefreshing = refreshing

                   )
                }

            ) {
            if(viewModel.images.isEmpty()){
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                    LottieAnimation(
                        composition = composition,
                        progress = {
                            progress
                        }
                    )
                    Text("No Favourites added yet", color = accent)
                }

            }else{

                ImagesVerticalGrid(
                    images = viewModel.images,


                    modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                    onImageClick = onImageClick,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                 

                )
            }


            }
        }


}