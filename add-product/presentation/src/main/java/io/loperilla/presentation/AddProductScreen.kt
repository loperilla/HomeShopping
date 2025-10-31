package io.loperilla.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.loperilla.designsystem.HomeShoppingTheme

@Composable
fun AddProductScreen(
    state: AddProductState,
    onEvent: (AddProductEvent) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    HomeShoppingTheme {
        AddProductScreen(
            state = AddProductState(),
            onEvent = {}
        )
    }
}