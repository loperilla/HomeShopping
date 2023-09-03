package io.loperilla.core_ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.camera
 * Created By Manuel Lopera on 3/9/23 at 09:41
 * All rights reserved 2023
 */
object BitmapUtils {
    /**
     * The rotationDegrees parameter is the rotation in degrees clockwise from the original orientation.
     */
    fun Bitmap.rotateBitmap(rotationDegrees: Int): Bitmap {
        val matrix = Matrix().apply {
            postRotate(-rotationDegrees.toFloat())
            postScale(-1f, -1f)
        }

        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

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

    fun Bitmap.toByteArray(): ByteArray {
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }
}
