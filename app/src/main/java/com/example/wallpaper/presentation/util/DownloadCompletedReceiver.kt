package com.example.wallpaper.presentation.util

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission

class DownloadCompletedReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if(id != -1L) {

                println("Download with ID $id finished!")
                val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                // Vibrate for 100ms using VibrationEffect (API 26+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                    vibrator.vibrate(effect)
                } else {
                    // For older versions
                    vibrator.vibrate(100)
                }
            }
        }
    }
}