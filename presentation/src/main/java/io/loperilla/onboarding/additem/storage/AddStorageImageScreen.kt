package io.loperilla.onboarding.additem.storage

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.loperilla.core_ui.util.BitmapUtils.uriToBitmap
import io.loperilla.onboarding_presentation.R
import timber.log.Timber

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem.storage
 * Created By Manuel Lopera on 3/9/23 at 12:10
 * All rights reserved 2023
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddStorageImageScreen(
    onImageSelected: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    val storagePermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        rememberPermissionState(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
    }

    if (photoUri != null) {
        val painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(context)
                .data(data = photoUri)
                .build()
        )

        Image(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            contentScale = ContentScale.FillBounds,
            painter = painter,
            contentDescription = null
        )
        uriToBitmap(context, photoUri!!)?.let {
            Timber.tag("StorageImage").i("uri to bitmap success")
            onImageSelected(it)
        }
        return
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (storagePermissionState.status.isGranted) {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                    return@clickable
                }

                if (storagePermissionState.status.shouldShowRationale) {
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
                    storagePermissionState.launchPermissionRequest()
                }
            }
    ) {
        Icon(
            imageVector = Icons.Filled.ImageSearch,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )

        val textMessage = when (val status = storagePermissionState.status) {
            PermissionStatus.Granted -> stringResource(R.string.select_storage_image_text)

            is PermissionStatus.Denied -> if (!status.shouldShowRationale) {
                stringResource(
                    R.string.press_to_settings_permission,
                    stringResource(R.string.storage)
                )
            } else {
                stringResource(
                    R.string.press_to_request_permission,
                    stringResource(R.string.storage)
                )
            }
        }

        Text(
            textMessage,
            modifier = Modifier
                .wrapContentWidth(),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
    }
}
