package io.loperilla.onboarding.addshoppingCart.addProduct

import android.Manifest
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.loperilla.core_ui.TextBodySize
import io.loperilla.onboarding.FlowCommerce
import io.loperilla.onboarding.commerces
import io.loperilla.onboarding.getTempUri
import io.loperilla.onboarding.uriToBitmap
import io.loperilla.onboarding_presentation.R
import io.loperilla.ui.CommonTopBar
import io.loperilla.ui.HomeShoppingBottomSheet
import io.loperilla.ui.HomeShoppingCard
import io.loperilla.ui.ItemBottomSheet
import io.loperilla.ui.Screen
import io.loperilla.ui.TransparentScaffold
import io.loperilla.ui.button.FormButton
import io.loperilla.ui.input.TextInput
import io.loperilla.ui.itemPadding
import io.loperilla.ui.previews.PIXEL_33_NIGHT
import io.loperilla.ui.text.TextRegular
import io.loperilla.ui.text.TextSemiBold
import io.loperilla.ui.text.TextTitle

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
    val photoUri = remember { mutableStateOf<Uri?>(null) }
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
    io.loperilla.ui.Screen {
        io.loperilla.ui.TransparentScaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                io.loperilla.ui.CommonTopBar(
                    stringResource(R.string.add_item_scaffold_title, commerceName),
                    navActionClick = { onEvent(AddProductEvent.NavigateBack) }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .itemPadding(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    io.loperilla.ui.input.TextInput(
                        text = state.newProductInputValue,
                        labelText = "Nombre del nuevo producto",
                        onTextChange = { newName ->
                            onEvent(AddProductEvent.NewProductInput(newName))
                        }
                    )
                    SelectAnotherCommerce(state = state, onEvent = onEvent)
                    io.loperilla.ui.text.TextRegular(text = "Agrega imagen a tu nuevo producto")
                    AnimatedVisibility(visible = state.showMenuToProductPhoto) {
                        io.loperilla.ui.HomeShoppingBottomSheet(
                            title = "Selecciona una imagen",
                            items = listOf(
                                io.loperilla.ui.ItemBottomSheet(
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
                                io.loperilla.ui.ItemBottomSheet(
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
                        io.loperilla.ui.HomeShoppingCard(
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
                                } ?: run {
                                    Icon(
                                        imageVector = Icons.Filled.CameraEnhance,
                                        contentDescription = "Empty image",
                                        modifier = Modifier
                                            .size(48.dp)
                                    )
                                    io.loperilla.ui.text.TextSemiBold(
                                        text = "Selecciona una imagen para tu nuevo producto",
                                        textAlign = TextAlign.Center
                                    )
                                }

                            }
                        }
                    }
                }


                io.loperilla.ui.button.FormButton(
                    textButton = "Agregar producto",
                    enableButton = state.validProduct,
                    modifier = Modifier
                        .imePadding()
                ) {
                    onEvent(AddProductEvent.AddProduct)
                }
            }
        }
    }
}

@Composable
fun SelectAnotherCommerce(
    state: AddProductState,
    onEvent: (AddProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationState by animateFloatAsState(
        targetValue = if (state.commerceListIsVisible) 180f else 0f, label = ""
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            io.loperilla.ui.text.TextTitle(
                text = "¿Quieres agregar otro comercio?",
                textSize = TextBodySize
            )
            IconButton(onClick = { onEvent(AddProductEvent.ChangeChipVisibility) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Icon show chips",
                    modifier = Modifier
                        .rotate(rotationState)
                )
            }

        }
        AnimatedVisibility(visible = state.commerceListIsVisible) {
            FlowCommerce(
                state.commerceList,
                onCommerceClicked = {
                    onEvent(AddProductEvent.SelectCommerce(it.id))
                }
            )
        }
    }
}

@io.loperilla.ui.previews.PIXEL_33_NIGHT
@Composable
private fun AddProductScreenPreview() {
    AddProductScreen(
        "Carrefour",
        state = AddProductState(),
        onEvent = {}
    )
}

@io.loperilla.ui.previews.PIXEL_33_NIGHT
@Composable
private fun AddProductScreenWithNamePreview() {
    AddProductScreen(
        "Carrefour",
        state = AddProductState(
            newProductInputValue = "Coca Cola",
            commerceList = commerces,
            commerceListIsVisible = true
        ),
        onEvent = {}
    )
}
