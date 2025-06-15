package com.example.wallpaper.presentation.wallpaperScreen

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.wallpaper.data.local.LocalDatabase


import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.data.model.FavouriteImageDto
import com.example.wallpaper.presentation.util.BitmapUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.inject.Inject


data class WallpaperScreenState(
    val isDownloading: Boolean = false,
    val imageSaved: Boolean = false,
    // Add other relevant state properties here
    // val error: String? = null,
    // val currentImage: YourImageType? = null
)
@HiltViewModel
class WallPaperViewModel@Inject constructor(
    localDatabase: LocalDatabase,
    private val application: Application
):ViewModel() {
    private val imagesDao=localDatabase.backendImageDao()
    private val favoriteImageDao=localDatabase.FavoriteImageDao()
    var isSettingWallpaper:Boolean by mutableStateOf(false)
    var image:BackendImageDto by mutableStateOf(BackendImageDto("","",0,"","","",0))
        private set
    val favouriteImageIds: StateFlow<List<String>> = favoriteImageDao.getImageIds().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )






    var Isdark:Boolean by mutableStateOf(false)
    var loading:Boolean by mutableStateOf(true)
    fun getImage(url:String){
        viewModelScope.launch {

        val result=imagesDao.getImageById(url)
            image=result
        }

    }

     fun CheckisDark(result: AsyncImagePainter.State.Success) {

        viewModelScope.launch {
            loading=true
            val bitmap = (result.result.drawable as BitmapDrawable).bitmap
            BitmapUtil.createBitmapCopy(bitmap)?.let { copyBitmap ->
                val isLight = withContext(Dispatchers.Default) {
                    BitmapUtil.isImageLight(copyBitmap)

                }
                Isdark=isLight
            }
           // delay(100)
            loading=false
        }



    }

        fun toggleFavourite(image: FavouriteImageDto) {
            viewModelScope.launch {
                val isFavourite = favoriteImageDao.CheckExists(image.id)
                if (isFavourite) {
                    favoriteImageDao.DeleteFavouriteImage(image)
                } else {
                    favoriteImageDao.insertFavouriteImage(image)

                }
            }
        }




    fun applywallpaper(context:Context,url:String,place:String){

        val wallpaperManager = WallpaperManager.getInstance(context)
        try{
        viewModelScope.launch {
            isSettingWallpaper=true
            val task= async(Dispatchers.IO){
                BitmapFactory.decodeStream(
                    URL(url).openConnection().getInputStream()
                )
            }
            val bitmap=task.await()
            if(place=="home"){
                wallpaperManager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_SYSTEM)
            }
            else if(place=="lock"){
                wallpaperManager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_LOCK)
            }
            else{
                wallpaperManager.setBitmap(bitmap)
            }
            isSettingWallpaper=false

        }
        }
        catch (e:Exception){
            isSettingWallpaper=false
            e.printStackTrace()
        }
    }













}
