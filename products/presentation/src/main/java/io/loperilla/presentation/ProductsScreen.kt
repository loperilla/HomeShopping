package io.loperilla.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.card.HomeShoppingCard
import io.loperilla.designsystem.composables.loading.AnimatedFullScreenLoading
import io.loperilla.designsystem.composables.swipe.SwipeBox
import io.loperilla.designsystem.composables.text.TextTitle
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.domain.model.product.Product

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
        floatingActionButton = {
            if (!state.isLoading) {
                FloatingActionButton(
                    onClick = {
                        onEvent(ProductsEvent.AddNewProduct)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new product"
                    )
                }
            }
        },
        modifier = modifier
    ) {
        AnimatedFullScreenLoading(state.isLoading, Modifier.padding(it))
        ProductListScreen(state, onEvent, Modifier.padding(it))
    }
}

@Composable
private fun ProductListScreen(
    state: ProductsState,
    onEvent: (ProductsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(!state.isLoading && state.productList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.productList.size) { index ->
                ProductItem(
                    product = state.productList[index],
                    onEvent = onEvent,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    onEvent: (ProductsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeBox(
        endToStartSwipe = {
            onEvent(ProductsEvent.RemoveProduct(product.id))
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HomeShoppingCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextTitle(
                product.name,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}


@PIXEL_33_NIGHT
@Composable
private fun ProductScreenPreview() {
    Screen {
        ProductsScreen(
            state = ProductsState(
                isLoading = true
            ),
            onEvent = {}
        )
    }
}

@PIXEL_33_NIGHT
@Composable
private fun ProductScreenListPreview() {
    val productList = (1..10).map {
        Product(
            id = it.toString(),
            name = "Producto $it"
        )
    }
    Screen {
        ProductsScreen(
            state = ProductsState(
                isLoading = false,
                productList = productList
            ),
            onEvent = {}
        )
    }
}