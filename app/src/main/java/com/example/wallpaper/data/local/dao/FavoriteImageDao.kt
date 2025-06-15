package com.example.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpaper.data.model.FavouriteImageDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImageDao {
    @Query("SELECT * FROM favourite_table")
    suspend fun getAllFavouriteImages(): List<FavouriteImageDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteImage(image: FavouriteImageDto)

    @Delete
    suspend fun DeleteFavouriteImage(image: FavouriteImageDto)

    @Query("SELECT EXISTS (SELECT 1 FROM favourite_table WHERE id = :id)")
    suspend fun CheckExists(id: String):Boolean

    @Query("SELECT id FROM favourite_table")
    fun getImageIds():Flow<List<String>>
}