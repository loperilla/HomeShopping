package io.loperilla.onboarding.additem.camera

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.camera
 * Created By Manuel Lopera on 3/9/23 at 09:41
 * All rights reserved 2023
 */
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraViewModel : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun onPhotoCaptured(bitmap: Bitmap) {
        // TODO: Process your photo, for example store it in the MediaStore
        // here we only do a dummy showcase implementation
        updateCapturedPhotoState(bitmap)
    }

    fun onCapturedPhotoConsumed() {
        updateCapturedPhotoState(null)
    }

    private fun updateCapturedPhotoState(updatedPhoto: Bitmap?) {
        _state.value.capturedImage?.recycle()
        _state.value = _state.value.copy(capturedImage = updatedPhoto)
    }

    override fun onCleared() {
        _state.value.capturedImage?.recycle()
        super.onCleared()
    }
}