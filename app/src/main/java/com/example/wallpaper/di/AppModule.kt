package com.example.wallpaper.di

import android.content.Context
import androidx.room.Room
import com.example.wallpaper.data.local.LocalDatabase
import com.example.wallpaper.data.remote.ApiService
import com.example.wallpaper.data.util.Constants
import com.example.wallpaper.data.util.Constants.DATABASE_NAME
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesBackendApiSevice():ApiService{
        val json = Json { ignoreUnknownKeys=true }
        @OptIn(ExperimentalSerializationApi::class)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(Constants.BASE_URL)
            .build()
        return  retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): LocalDatabase{
            return Room.databaseBuilder(context,LocalDatabase::class.java,DATABASE_NAME).build()
    }
}