package com.example.wallpaper.presentation.favourites_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.data.local.LocalDatabase
import com.example.wallpaper.data.local.mapToBackendImageDtoList
import com.example.wallpaper.data.model.BackendImageDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class favouriteViewmodel @Inject constructor(
    localDatabase: LocalDatabase
):ViewModel() {
    private val favoriteImageDao=localDatabase.FavoriteImageDao()
    var images:List<BackendImageDto> by mutableStateOf(emptyList())
        private set
    fun getAllFavourites(){
        viewModelScope.launch {
            val result=favoriteImageDao.getAllFavouriteImages()
            images=result.mapToBackendImageDtoList()
        }

    }
    init{
        getAllFavourites()
    }
}