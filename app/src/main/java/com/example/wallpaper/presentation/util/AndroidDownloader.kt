package com.example.wallpaper.presentation.util

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.core.net.toUri

class AndroidDownloader( private val context: Context) {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    fun downloadFile(url: String, title: String): Long {

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("$title.jpg")
            .addRequestHeader("Authorization", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.jpg")
        return downloadManager.enqueue(request)
    }
}