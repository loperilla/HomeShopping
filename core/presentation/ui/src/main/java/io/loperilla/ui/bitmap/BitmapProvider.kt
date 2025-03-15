package io.loperilla.ui.bitmap

import android.graphics.Bitmap
import android.net.Uri

/*****
 * Project: HomeShopping
 * From: io.loperilla.ui.bitmap
 * Created By Manuel Lopera on 14/3/25 at 19:01
 * All rights reserved 2025
 */
interface BitmapProvider {
    fun uriToBitmap(selectedUri: Uri?): Bitmap?
    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray?
    fun uriToByteArray(selectedUri: Uri?): ByteArray?
}