package io.loperilla.onboarding.addshoppingCart.addProduct

import android.Manifest
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.loperilla.core_ui.CommonTopBar
import io.loperilla.core_ui.HomeShoppingBottomSheet
import io.loperilla.core_ui.HomeShoppingCard
import io.loperilla.core_ui.ItemBottomSheet
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TransparentScaffold
import io.loperilla.core_ui.input.NewTextInput
import io.loperilla.core_ui.itemPadding
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextRegular
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.onboarding.additem.getTempUri
import io.loperilla.onboarding.additem.uriToBitmap
import io.loperilla.onboarding_presentation.R

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:55
 * All rights reserved 2024
 */

@Composable
fun AddProductScreen(
    commerceName: String,
    state: AddProductState,
    onEvent: (AddProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var photoUri= remember { mutableStateOf<Uri?>(null) }
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            photoUri.value = it
            onEvent(
                AddProductEvent.NewPhoto(
                    uriToBitmap(context, it)!!
                )
            )
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if (isSaved) {
                photoUri.value?.let {
                    onEvent(
                        AddProductEvent.NewPhoto(
                            uriToBitmap(context, it)!!
                        )
                    )
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { success ->
            if (success) {
                // Permission is granted, launch takePhotoLauncher
                val tmpUri = getTempUri(context)
                photoUri.value = tmpUri
                photoUri.value?.let {
                    takePhotoLauncher.launch(it)
                }
            }
        }
    )

    BackHandler {
        onEvent(AddProductEvent.NavigateBack)
    }
    Screen {
        TransparentScaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                CommonTopBar(
                    stringResource(R.string.add_item_scaffold_title, commerceName),
                    navActionClick = { onEvent(AddProductEvent.NavigateBack) }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onEvent(AddProductEvent.AddProduct) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.fab_createshoppingcart_content_description)
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .itemPadding(),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp
                )
            ) {
                NewTextInput(
                    text = state.newProductInputValue,
                    labelText = "Nombre del nuevo producto",
                    onTextChange = { newName ->
                        onEvent(AddProductEvent.NewProductInput(newName))
                    }
                )
                TextRegular(text = "Agrega imagen a tu nuevo producto")
                AnimatedVisibility(visible = state.showMenuToProductPhoto) {
                    HomeShoppingBottomSheet(
                        title = "Selecciona una imagen",
                        items = listOf(
                            ItemBottomSheet(
                                title = "Galería",
                                icon = Icons.Filled.Photo,
                                onClick = {
                                    pickImageLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                            ),
                            ItemBottomSheet(
                                title = "Camara",
                                icon = Icons.Filled.Camera,
                                onClick = {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                        )
                    ),
                        onDismiss = {
                            onEvent(AddProductEvent.HideMenuToSelectPhoto)
                        }
                    )
                }

                AnimatedVisibility(visible = !state.showMenuToProductPhoto) {
                    HomeShoppingCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    onEvent(
                                        AddProductEvent.ShowMenuToSelectPhoto
                                    )
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraEnhance,
                                contentDescription = "Empty image",
                                modifier = Modifier
                                    .size(48.dp)
                            )
                            TextSemiBold(
                                text = "Selecciona una imagen para tu nuevo producto",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                state.bitmapSelected?.let {
                    val painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(context)
                            .data(data = it)
                            .build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                    )
                }
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun AddProductScreenPreview() {
    AddProductScreen(
        "Carrefour",
        state = AddProductState(),
        onEvent = {}
    )
}

@PIXEL_33_NIGHT
@Composable
private fun AddProductScreenWithMenuPreview() {
    AddProductScreen(
        "Carrefour",
        state = AddProductState(
            showMenuToProductPhoto = true
        ),
        onEvent = {}
    )
}
