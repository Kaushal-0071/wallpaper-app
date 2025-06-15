package com.example.wallpaper.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wallpaper.data.local.LocalDatabase
import com.example.wallpaper.data.remote.ApiService
import com.example.wallpaper.data.model.BackendImageDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    localDatabase: LocalDatabase


):ViewModel() {
    var images:List<BackendImageDto> by mutableStateOf(emptyList())
        private set
    private var imagesDao=localDatabase.backendImageDao()

    init{
        viewModelScope.launch {

            getImages()
        }
    }
    private suspend fun getImages(){
       val job= viewModelScope.launch {
            images= apiService.getAllImages()

        }
        job.join()
        imagesDao.insertAllImages(images)
        viewModelScope.launch {
            val result=imagesDao.getAllImages()
            images=result
        }
    }
}