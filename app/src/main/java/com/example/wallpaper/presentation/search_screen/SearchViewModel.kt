package com.example.wallpaper.presentation.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    var images:List<BackendImageDto> by mutableStateOf(emptyList())
    var success:Boolean by mutableStateOf(false)



    fun searchImages(query: String){
        viewModelScope.launch {
            val result= apiService.searchImages(query)
            images=result
            if(result.isNotEmpty()){
                success=true
            }else{
                success=false

            }
        }

    }

}