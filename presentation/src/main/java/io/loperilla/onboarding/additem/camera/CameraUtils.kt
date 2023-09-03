package io.loperilla.onboarding.additem.camera

import android.graphics.Bitmap
import android.graphics.Matrix

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.camera
 * Created By Manuel Lopera on 3/9/23 at 09:41
 * All rights reserved 2023
 */
object CameraUtils {
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
}
