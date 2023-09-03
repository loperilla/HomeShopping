package io.loperilla.onboarding.additem.camera

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.camera
 * Created By Manuel Lopera on 3/9/23 at 09:41
 * All rights reserved 2023
 */
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    private var _userWantToTakeAPhoto: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userWantToTakeAPhoto: StateFlow<Boolean> = _userWantToTakeAPhoto

    fun onPhotoCaptured(bitmap: Bitmap) {
        updateCapturedPhotoState(bitmap)
    }

    fun onCapturedPhotoConsumed() {
        updateCapturedPhotoState(null)
    }

    fun takeAPhoto() {
        viewModelScope.launch {
            _userWantToTakeAPhoto.value = true
        }
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