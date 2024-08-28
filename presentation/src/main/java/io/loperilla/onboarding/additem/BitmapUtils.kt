package io.loperilla.onboarding.additem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import io.loperilla.onboarding_presentation.R
import java.io.File
import java.io.FileDescriptor
import java.io.IOException

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 27/8/24 at 19:24
 * All rights reserved 2024
 */

fun uriToBitmap(context: Context, selectedFileUri: Uri): Bitmap? {
    try {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(selectedFileUri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

// for takePhotoLauncher used
fun getTempUri(context: Context): Uri? {
    val directory = File(context.cacheDir, "images")
    val authority = context.getString(R.string.fileprovider)
    directory.mkdirs()
    val file = File.createTempFile(
        "image_" + System.currentTimeMillis().toString(),
        ".jpg",
        directory
    )

    return FileProvider.getUriForFile(
        context,
        authority,
        file
    )
}
