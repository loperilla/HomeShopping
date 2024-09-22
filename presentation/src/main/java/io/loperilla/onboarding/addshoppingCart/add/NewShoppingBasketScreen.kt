package io.loperilla.onboarding.addshoppingCart.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import io.loperilla.core_ui.CommonTopBar
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.TransparentScaffold
import io.loperilla.core_ui.itemPadding
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextRegular
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.onboarding.commerce.SwipeBox
import io.loperilla.onboarding.products
import io.loperilla.onboarding_domain.model.database.product.Product
import io.loperilla.onboarding_presentation.R

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 26/8/23 at 17:13
 * All rights reserved 2023
 */

@Composable
fun NewShoppingBasketScreen(
    commerceSelectedName: String,
    state: NewShoppingBasketState,
    onEvent: (NewShoppingBasketEvent) -> Unit
) {
    Screen {
        TransparentScaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CommonTopBar(
                    stringResource(R.string.addshoppingcart_scaffold_title, commerceSelectedName),
                    navActionClick = { onEvent(NewShoppingBasketEvent.NavigateBack) }
                )
            },
            floatingActionButton = {
                if (!state.searchBarActive) {
                    ExtendedFloatingActionButton(
                        onClick = { onEvent(NewShoppingBasketEvent.AddItem) },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCartCheckout,
                            contentDescription = stringResource(R.string.add_item_fab_text_value),
                            modifier = Modifier
                                .padding(end = LOW)
                        )
                        TextSemiBold(
                            stringResource(R.string.shopping_cart_fab),
                            textSize = TextSmallSize
                        )
                    }
                } else {
                    SmallFloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White,
                        shape = CircleShape,
                        onClick = { onEvent(NewShoppingBasketEvent.ChangeSearchBarActive(false)) },
                        modifier = Modifier
                            .imePadding()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.add_item_fab_text_value)
                        )
                    }
                }
            }
        ) {
            AddShoppingBasketScreen(
                state,
                onEvent,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun AddShoppingBasketScreen(
    state: NewShoppingBasketState,
    onEvent: (NewShoppingBasketEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(LOW)
    ) {
        ProductsSearchBar(state, onEvent)
        ProductsList(state.filteredItemShoppingList, onEvent)
    }
}

@Composable
private fun ProductsList(
    productList: List<Product>,
    onEvent: (NewShoppingBasketEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)
    ) {
        items(
            count = productList.size
        ) { index ->
            ItemShopping(
                productList[index],
                onEvent
            )
        }
        item {
            Column(
                modifier = modifier
                    .itemPadding()
                    .clickable { onEvent(NewShoppingBasketEvent.AddItem) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(LOW)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = "Add shopping cart",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                TextRegular(
                    text = "Crea un nuevo\nproducto",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductsSearchBar(
    state: NewShoppingBasketState,
    onEvent: (NewShoppingBasketEvent) -> Unit
) {
    SearchBarDefaults.inputFieldColors(
        focusedTextColor = Color.White.copy(
            alpha = 0.9f
        ),
        unfocusedTextColor = Color.White.copy(
            alpha = 0.9f
        )
    )
    val colors = colors(containerColor = if (state.searchBarActive) Color.Transparent else colors().containerColor )
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = state.searchBarQueryValue,
                onQueryChange = {
                    onEvent(NewShoppingBasketEvent.SearchQueryChanged(it))
                },
                onSearch = {
                    onEvent(NewShoppingBasketEvent.SearchShoppingProductWithCurrentValue(it))
                },
                expanded = state.searchBarActive,
                onExpandedChange = {
                    onEvent(NewShoppingBasketEvent.ChangeSearchBarActive(it))
                },
                enabled = true,
                placeholder = {
                    Text(stringResource(R.string.seachbar_placeholder_text))
                },
                leadingIcon = null,
                trailingIcon = {
                    IconButton(onClick = { onEvent(NewShoppingBasketEvent.CleanSearchBarInputValue) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Backspace,
                            contentDescription = stringResource(R.string.clear_searchbar_content_description_icon)
                        )
                    }
                },
                colors = colors.inputFieldColors,
                interactionSource = null,
            )
        },
        expanded = state.searchBarActive,
        onExpandedChange = {
            onEvent(NewShoppingBasketEvent.ChangeSearchBarActive(it))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LOW),
        shape = SearchBarDefaults.inputFieldShape,
        colors = colors,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        content = {
            PreviousQueryList(
                previousQueryList = state.filteredQueryList,
                onEvent = onEvent
            )
        },
    )
}

@Composable
private fun PreviousQueryList(
    previousQueryList: List<String>,
    onEvent: (NewShoppingBasketEvent) -> Unit
) {
    if (previousQueryList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM),
            verticalArrangement = Arrangement.spacedBy(MEDIUM)
        ) {
            items(previousQueryList.size) {
                SwipeBox(
                    endToStartSwipe = {
                        onEvent(
                            NewShoppingBasketEvent.RemoveQuery(
                                previousQueryList[it]
                            )
                        )
                    }
                ) {
                    TextSemiBold(
                        text = previousQueryList[it],
                        textColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                onEvent(
                                    NewShoppingBasketEvent.QueryClicked(
                                        previousQueryList[it]
                                    )
                                )
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemShopping(
    item: Product,
    onEvent: (NewShoppingBasketEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable { onEvent },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LOW)
    ) {
        if (item.imageUrl.isNotEmpty()) {
            SubcomposeAsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .memoryCacheKey("${item.name}_image")
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                modifier = Modifier
                    .size(100.dp)
                    .padding(LOW)
                    .clip(RoundedCornerShape(25)),
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "${item.name}_not_fount",
                    )
                },
                contentDescription = "${item.name}_image",
                filterQuality = FilterQuality.High
            )
//            https://cdn.icon-icons.com/icons2/16/PNG/256/fruit_apple_food_1815.png
        } else {
            Icon(
                imageVector = Icons.Outlined.AddAPhoto,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        TextRegular(
            text = item.name
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun AddShoppingBasketScreenPrev() {
    Screen {
        NewShoppingBasketScreen(
            commerceSelectedName = "Mercadona",
            state = NewShoppingBasketState(),
            onEvent = {}
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun AddShoppingBasketScreenShoppingItemsPrev() {
    Screen {
        NewShoppingBasketScreen(
            commerceSelectedName = "Mercadona",
            state = NewShoppingBasketState(
                searchBarActive = false,
                previousQueryList = listOf("Manzana", "Pera"),
                searchBarQueryValue = "p",
                productList = products.plus(products)
            ),
            onEvent = {}
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun ShowSearchBarContentPrev() {
    Screen {
        NewShoppingBasketScreen(
            commerceSelectedName = "Mercadona",
            state = NewShoppingBasketState(
                searchBarActive = true,
                previousQueryList = listOf("Manzana", "Pera"),
                searchBarQueryValue = "",
            ),
            onEvent = {}
        )
    }
}