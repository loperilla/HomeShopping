package io.loperilla.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.loading.AnimatedFullScreenLoading
import io.loperilla.designsystem.composables.text.TextTitle
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT

@Composable
fun ProductsScreen(
    state: ProductsState,
    onEvent: (ProductsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    TransparentScaffold(
        topBar = {
            if (!state.isLoading) {
                CommonTopBar(
                    topBarText = "Productos",
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    navActionClick = {
                        onEvent(ProductsEvent.GoBack)
                    }
                )
            }
        },
        modifier = modifier
    ) {
        AnimatedFullScreenLoading(state.isLoading, Modifier.padding(it))
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.productList.forEach { product ->
                    TextTitle(text = product.name)
                }
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun Preview() {
    Screen {
        ProductsScreen(
            state = ProductsState(),
            onEvent = {}
        )
    }
}