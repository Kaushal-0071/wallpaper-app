package com.example.wallpaper.data.local


import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.data.model.FavouriteImageDto

fun FavouriteImageDto.toBackendImageDto(): BackendImageDto {
    return BackendImageDto(
        creator = this.creator,
        creatorUrl = this.creatorUrl,
        height = this.height,
        id = this.id,
        name = this.name,
        url = this.url,
        width = this.width

    )
}

fun BackendImageDto.toFavouriteImageDto(): FavouriteImageDto {
    return FavouriteImageDto(
        creator = this.creator,
        creatorUrl = this.creatorUrl,
        height = this.height,
        id = this.id,
        name = this.name,
        url = this.url,
        width = this.width
    )
}
fun List<FavouriteImageDto>.mapToBackendImageDtoList(): List<BackendImageDto> {
    return this.map { it.toBackendImageDto() }
}

// Extension function to convert a List of BackendImageDto to a List of FavouriteImageDto
fun List<BackendImageDto>.mapToFavouriteImageDtoList(): List<FavouriteImageDto> {
    return this.map { it.toFavouriteImageDto() }
}