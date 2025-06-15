package com.example.wallpaper.data.remote

import com.example.wallpaper.data.model.BackendImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/images")
    suspend fun getAllImages():List<BackendImageDto>

    @GET("/api/images/search")
    suspend fun searchImages(
        @Query("q")q:String
    ):List<BackendImageDto>

}