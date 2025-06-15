package com.example.wallpaper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wallpaper.data.local.dao.BackendImageDao
import com.example.wallpaper.data.local.dao.FavoriteImageDao
import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.data.model.FavouriteImageDto


@Database(entities = [BackendImageDto::class,FavouriteImageDto::class], version = 2)
abstract class LocalDatabase :RoomDatabase(){
    abstract fun backendImageDao(): BackendImageDao
    abstract fun FavoriteImageDao():FavoriteImageDao
}