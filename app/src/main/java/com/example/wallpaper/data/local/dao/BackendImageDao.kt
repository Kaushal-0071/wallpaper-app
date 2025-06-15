package com.example.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaper.data.model.BackendImageDto

@Dao
interface BackendImageDao {
    @Query("SELECT * FROM image_table")
    suspend fun getAllImages(): List<BackendImageDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImages(images: List<BackendImageDto>)

    @Query("SELECT * FROM image_table WHERE url = :url")
    suspend fun getImageById(url: String): BackendImageDto

}