package com.example.wallpaper.presentation.util

import android.graphics.Bitmap

import android.graphics.Color as AndroidColor // Use an alias to avoid name clash if needed
import androidx.core.graphics.get

object BitmapUtil {
    fun isImageLight(bitmap: Bitmap): Boolean {
        var totalLuminance = 0.0
        val width = bitmap.width
        val height = bitmap.height

        if (width == 0 || height == 0) {
            // Avoid division by zero for empty bitmaps
            return false // Or true, depending on desired default for empty images
        }

        val totalPixels = width * height

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixelColorInt = bitmap[x, y] // This returns an Int

                // Use android.graphics.Color to extract components from the Int
                val luminance =
                    (AndroidColor.red(pixelColorInt) * 0.299 + // Use AndroidColor.red
                            AndroidColor.green(pixelColorInt) * 0.587 + // Use AndroidColor.green
                            AndroidColor.blue(pixelColorInt) * 0.114    // Use AndroidColor.blue
                            ) / 255.0 // Use 255.0 for floating point division

                totalLuminance += luminance
            }
        }

        val averageLuminance = totalLuminance / totalPixels
        return averageLuminance > 0.7 // Threshold can be adjusted
    }

    fun createBitmapCopy(bitmap: Bitmap): Bitmap? {
        // This function seems fine as is, but ensure the source bitmap is not recycled
        // if you intend to use it after this call.
        // The 'true' for isMutable in copy() is often not necessary unless you plan to draw on the copy.
        // Consider `bitmap.copy(Bitmap.Config.ARGB_8888, false)` if immutability is acceptable.
        return runCatching {
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        }.getOrNull()
    }
}