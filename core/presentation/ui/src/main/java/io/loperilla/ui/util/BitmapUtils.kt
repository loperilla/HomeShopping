package io.loperilla.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import io.loperilla.ui.bitmap.BitmapProvider
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.camera
 * Created By Manuel Lopera on 3/9/23 at 09:41
 * All rights reserved 2023
 */
class BitmapUtilsImpl(
    private val context: Context
) : BitmapProvider {
    override fun uriToBitmap(selectedUri: Uri?): Bitmap? = try {
        val parcelFileDescriptor = context
            .contentResolver.openFileDescriptor(selectedUri!!, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        image
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }

    override fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? = try {
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.toByteArray()
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }

    override fun uriToByteArray(selectedUri: Uri?): ByteArray? = try {
        val bitmap = uriToBitmap(selectedUri)
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.toByteArray()
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}
