package com.example.wallpaper.presentation.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarDefaults.floatingToolbarVerticalNestedScroll
import androidx.compose.material3.FloatingToolbarExitDirection
import androidx.compose.material3.FloatingToolbarHorizontalFabPosition
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.presentation.components.ImagesVerticalGrid
import com.example.wallpaper.presentation.theme.Background
import com.example.wallpaper.presentation.theme.accent
import com.example.wallpaper.presentation.theme.icons
import kotlinx.coroutines.delay
import kotlin.math.abs

@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun SharedTransitionScope.HomeScreen(
    images: List<BackendImageDto>,
    onImageClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onFabClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val fabscrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
        FloatingToolbarExitDirection.End
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Wallpaper") },
                scrollBehavior = scrollBehavior,

                actions = {
                    FilledIconButton(onSearchClick,
                        colors = IconButtonColors(containerColor = accent, contentColor = icons, disabledContentColor = Color.DarkGray, disabledContainerColor = icons)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                            , tint = icons
                        )
                    }
                }
                ,
                colors=TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    scrolledContainerColor = Background
                )
            )
        },
        floatingActionButton = {

            HorizontalFloatingToolbar(
                false,
                floatingActionButton = {
                    FloatingActionButton(onClick = onFabClick, modifier = Modifier.offset(20.dp,10.dp), containerColor = icons) {
                        Icon(Icons.Filled.Favorite, "Add", tint = accent)
                    }
                },
                scrollBehavior = fabscrollBehavior,
                collapsedShadowElevation = 0.dp,
                floatingActionButtonPosition = FloatingToolbarHorizontalFabPosition.End

                ) {


            }
        }


    ) { innerPadding ->

        ImagesVerticalGrid(
            images = images,

            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .nestedScroll(fabscrollBehavior),
            onImageClick = onImageClick,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope

        )


    }
}

