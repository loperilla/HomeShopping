package io.loperilla.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.button.SimpleButton
import io.loperilla.designsystem.composables.input.TextInput
import io.loperilla.designsystem.composables.loading.AnimatedFullScreenLoading
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT

@Composable
fun AddProductScreen(
    state: AddProductState,
    onEvent: (AddProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    TransparentScaffold(
        topBar = {
            if (!state.isLoading) {
                CommonTopBar(
                    topBarText = "Productos",
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    navActionClick = {
                        onEvent(AddProductEvent.GoBack)
                    }
                )
            }
        },
        modifier = modifier
    ) {
        AnimatedFullScreenLoading(state.isLoading, Modifier.padding(it))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextInput(
                text = state.newProductName,
                onTextChange = { newName ->
                    onEvent(AddProductEvent.OnProductNameChange(newName))
                },
                labelText = "Nombre",
                placeholderValue = "Nuevo nombre",
                onKeyBoardDoneAction = {
                    onEvent(AddProductEvent.AddNewProduct)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            SimpleButton(
                textButton = "Añadir producto",
                onClickButton = {
                    onEvent(AddProductEvent.AddNewProduct)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun Preview() {
    Screen {
        AddProductScreen(
            state = AddProductState(
                newProductName = "Nuevo producto",
                isLoading = false,
                isValidForm = false
            ),
            onEvent = {}
        )
    }
}
