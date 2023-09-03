package io.loperilla.onboarding.additem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import io.loperilla.onboarding.additem.camera.CameraUtils.rotateBitmap
import io.loperilla.onboarding.additem.camera.CameraViewModel
import timber.log.Timber

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 3/9/23 at 10:05
 * All rights reserved 2023
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddCameraImageScreen(
    userWantTakeAPhoto: Boolean,
    userGoToTakeAPhoto: () -> Unit,
    onPhotoCaptured: () -> Unit
) {
    val context = LocalContext.current.applicationContext
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }
    val cameraViewModel: CameraViewModel = viewModel()

    val cameraState by cameraViewModel.state.collectAsStateWithLifecycle()
    if (cameraState.capturedImage != null) {
        onPhotoCaptured()
        ImageCaptured(context, cameraState.capturedImage!!)
        return
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (userWantTakeAPhoto) {
                ExtendedFloatingActionButton(
                    onClick = {
                        val mainExecutor = ContextCompat.getMainExecutor(context)

                        cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                val correctedBitmap: Bitmap = image
                                    .toBitmap()
                                    .rotateBitmap(image.imageInfo.rotationDegrees)

                                cameraViewModel.onPhotoCaptured(correctedBitmap)

                                image.close()
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Timber.tag("CameraContent").e(exception, "Error capturing image")
                            }
                        })
                    }
                ) {
                    Text(text = "Take photo")
                }
            }
        }
    ) { paddingValues: PaddingValues ->
        when (val status = cameraPermissionState.status) {
            PermissionStatus.Granted -> {
                userGoToTakeAPhoto()
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    factory = { context ->
                        PreviewView(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                            scaleType = PreviewView.ScaleType.FILL_START
                        }.also { previewView ->
                            previewView.controller = cameraController
                            cameraController.bindToLifecycle(lifecycleOwner)
                        }
                    }
                )
            }

            is PermissionStatus.Denied -> {
                AddCameraImage(
                    context,
                    status.shouldShowRationale,
                    cameraPermissionState::launchPermissionRequest,
                )
            }
        }
    }
}

@Composable
fun AddCameraImage(
    context: Context,
    permissionShouldShowRationale: Boolean,
    requestPermission: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (permissionShouldShowRationale) {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    ContextCompat.startActivity(
                        context,
                        intent,
                        null
                    )
                } else {
                    requestPermission()
                }
            }
    ) {

        Icon(
            imageVector = Icons.Filled.AddAPhoto,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )

        Text(
            if (permissionShouldShowRationale) {
                "Pulse para ir a los ajustes y habilitarlo a mano"
            } else {
                "Pulse para aceptar los permisos"
            },
            modifier = Modifier
                .wrapContentWidth(),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
    }

}

@Composable
fun ImageCaptured(
    context: Context,
    bitmap: Bitmap
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(context)
            .data(data = bitmap)
            .build()
    )

    Image(
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds,
        painter = painter,
        contentDescription = null
    )
}