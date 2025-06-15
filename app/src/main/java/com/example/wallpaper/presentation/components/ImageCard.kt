package com.example.wallpaper.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.example.wallpaper.data.model.BackendImageDto



@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: BackendImageDto?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(image?.url)
        .crossfade(true)
        .placeholderMemoryCacheKey(MemoryCache.Key(image?.url?:""))
        .build()
    val aspectRatio: Float by remember {
        derivedStateOf { (image?.width?.toFloat() ?: 1f) / (image?.height?.toFloat() ?: 1f) }
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .then(modifier)
    ) {
        Box {
            if (image != null) {
                with(sharedTransitionScope){
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize().sharedElement(
                                sharedContentState = rememberSharedContentState(image.url),
                                animatedVisibilityScope =animatedVisibilityScope,
                                boundsTransform = {
                                    _,_->
                                    tween(300)
                                }
                            )

                    )
                }




            }

        }
    }
}
