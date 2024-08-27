package io.loperilla.onboarding.addshoppingCart.addProduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.loperilla.core_ui.CommonTopBar
import io.loperilla.core_ui.HomeShoppingCard
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TransparentScaffold
import io.loperilla.core_ui.input.NewTextInput
import io.loperilla.core_ui.itemPadding
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextRegular
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.onboarding_presentation.R

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 26/8/24 at 18:55
 * All rights reserved 2024
 */

@Composable
fun AddProductScreen(
    state: AddProductState,
    onEvent: (AddProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Screen {
        TransparentScaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                CommonTopBar(
                    stringResource(R.string.add_item_scaffold_title),
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
                    .itemPadding()
            ) {
                NewTextInput(
                    text = state.newProductInputValue,
                    labelText = "Nombre del nuevo producto",
                    onTextChange = { newName ->
                        onEvent(AddProductEvent.NewProductInput(newName))
                    }
                )
                TextRegular(text = "Agrega imagen a tu nuevo producto")
                Spacer(modifier = Modifier.height(16.dp))
                HomeShoppingCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
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
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun AddProductScreenPreview() {
    AddProductScreen(
        state = AddProductState(),
        onEvent = {}
    )
}
