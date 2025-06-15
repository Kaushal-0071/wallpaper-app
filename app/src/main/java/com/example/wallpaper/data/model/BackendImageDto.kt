package com.example.wallpaper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wallpaper.data.util.Constants.FAVOURITE_TABLE
import com.example.wallpaper.data.util.Constants.IMAGE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = IMAGE_TABLE)
data class BackendImageDto(
    val creator: String,
    @SerialName("creator_url")
    val creatorUrl: String,

    val height: Int,
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val width: Int
)

@Serializable
@Entity(tableName = FAVOURITE_TABLE)
data class FavouriteImageDto(
    val creator: String,
    @SerialName("creator_url")
    val creatorUrl: String,

    val height: Int,
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val width: Int
)