package com.example.wallpaper.presentation.wallpaperScreen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.outlined.ArrowCircleDown
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.example.wallpaper.data.local.toFavouriteImageDto
import com.example.wallpaper.data.model.FavouriteImageDto
import com.example.wallpaper.presentation.util.AndroidDownloader
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun SharedTransitionScope.WallPaperScreen(url :String,
                                           animatedVisibilityScope: AnimatedVisibilityScope

){

    val context = LocalContext.current
    val downloader = AndroidDownloader(context)
    val window= context.findActivity()?.window
    val insetsController = WindowCompat.getInsetsController(window, window?.decorView)

    insetsController.apply {
        hide(WindowInsetsCompat.Type.statusBars())
        hide(WindowInsetsCompat.Type.navigationBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
    }

   val decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
    val viewModel:WallPaperViewModel= hiltViewModel()
    viewModel.getImage(url)
   
    val favids by viewModel.favouriteImageIds.collectAsStateWithLifecycle()
    val Image=viewModel.image.toFavouriteImageDto()
    val isdark:Boolean by remember { mutableStateOf(false) }
    var isSheetOpen by rememberSaveable  { mutableStateOf(false) }
    val vibrantColors = FloatingToolbarDefaults.vibrantFloatingToolbarColors()
    var isWallpaperSheetOpen by rememberSaveable  { mutableStateOf(false) }
    val SheetState= rememberModalBottomSheetState()
    val ApplyWallPaperSheetState= rememberModalBottomSheetState()
    val imageRequest = ImageRequest.Builder(context)
        .data(decodedUrl)

        .crossfade(true)
        .placeholderMemoryCacheKey(MemoryCache.Key(decodedUrl?:""))
        .build()
    val uriHandler = LocalUriHandler.current

    val showToast by viewModel.showDownloadToast.collectAsState()
    // val screenState = viewModel.state // You'd also observe this

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Image Downloaded!", Toast.LENGTH_SHORT).show()
            viewModel.onDownloadToastShown() // Reset the event
        }
    }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){


            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(decodedUrl),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(300)
                        }),
                onSuccess = {
                    result->viewModel.CheckisDark(result)

                }

            )



            AnimatedVisibility(!viewModel.loading, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)) {
                IconButton(onClick = {isSheetOpen=!isSheetOpen} ){
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = if(!viewModel.Isdark){
                            Color.White
                        } else{
                            Color.Black
                        }
                    )
                }
            }


            if(isSheetOpen){
            ModalBottomSheet(
                onDismissRequest = {isSheetOpen=false},
                modifier = Modifier,
                sheetState = SheetState

            ) {
                Data(image = Image,uriHandler)

            }

            }
            if(isWallpaperSheetOpen){
                ModalBottomSheet(
                    onDismissRequest = {isWallpaperSheetOpen=false},
                    modifier = Modifier,
                    sheetState = ApplyWallPaperSheetState){
                    setBottomSheet(
                        SetHome = {
                            viewModel.applywallpaper(context,decodedUrl,"home")
                            isWallpaperSheetOpen=false
                        },
                        SetLock ={ viewModel.applywallpaper(context,decodedUrl,"lock")
                            isWallpaperSheetOpen=false
                        },
                        SetBoth = {
                            viewModel.applywallpaper(context,decodedUrl,"both")
                            isWallpaperSheetOpen=false

                        }
                    )
                }
            }



            AnimatedVisibility(!viewModel.loading, modifier =Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -ScreenOffset)){
                HorizontalFloatingToolbar(
                    expanded = true,
                    modifier =
                        Modifier,
                    colors = vibrantColors,
                    content = {

                        IconButton(onClick = { downloader.downloadFile(
                            url = Image.url,
                            title = Image.name
                        ) }) {
                            Icon(Icons.Rounded.ArrowCircleDown, contentDescription = "Localized description")
                        }
                        IconButton(onClick = { isWallpaperSheetOpen=!isWallpaperSheetOpen }) {
                            Icon(Icons.Rounded.CheckCircle, contentDescription = "Localized description")
                        }
                        IconButton(onClick = { viewModel.toggleFavourite(Image) }) {
                            if(favids.contains(Image.id)){ Icon(Icons.Filled.Favorite, contentDescription = "Localized description")}
                            else{
                                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Localized description")
                            }

                        }

                    }
                )
            }

        }





    
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun setBottomSheet(
    modifier: Modifier = Modifier,
    SetHome:()->Unit,
    SetLock:()->Unit,
    SetBoth:()->Unit
){

    Column (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Text("Set your Wallpaper" , fontWeight = FontWeight.Bold, fontSize = 22.sp )
        Button(onClick =SetHome , modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp)) {
            Icon(Icons.Filled.Home, contentDescription = "set wallpaper on homescreeen",modifier=Modifier.padding(end=10.dp))

            Text("Home Screen")
        }
        Button(onClick = SetLock , modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 2.dp)) {
            Icon(Icons.Filled.Lock, contentDescription = "set wallpaper on homescreeen",modifier=Modifier.padding(end=10.dp))

            Text("Lock Screen")
        }
        Button(onClick = SetBoth , modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp)) {
            Icon(Icons.Filled.PhoneIphone, contentDescription = "set wallpaper on homescreeen",modifier=Modifier.padding(end=10.dp))

            Text("Both")
        }
    }
}
















@Composable
fun Data(image:FavouriteImageDto,uriHandler: UriHandler){
    Column (modifier = Modifier.padding(horizontal = 15.dp)){
        Text(image.name, fontWeight=FontWeight.Bold , fontSize = 22.sp)
        Text("@"+image.creator, color = Color(14,128,242),
            modifier =Modifier
                .padding(2.dp)
                .clickable {
                    uriHandler.openUri(image.creatorUrl)
                })

        Text("Dimensions", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier
            .padding(2.dp)
            .padding(vertical = 8.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
        Row (Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("width", color = Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)
            Text(image.width.toString())
        }
        HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

        Row (Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("height", color = Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)
            Text(image.height.toString())
        }

    }

}