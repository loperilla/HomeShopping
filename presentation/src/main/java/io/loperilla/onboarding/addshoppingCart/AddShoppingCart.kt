package io.loperilla.onboarding.addshoppingCart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.core_ui.text.TextTitle
import io.loperilla.onboarding_domain.model.database.ShoppingItem
import io.loperilla.onboarding_presentation.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart
 * Created By Manuel Lopera on 26/8/23 at 17:13
 * All rights reserved 2023
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun AddShoppingCart(
    popBackStack: () -> Unit,
    navigateToNewItem: () -> Unit
) {
    val viewModel: AddShoppingCartViewModel = hiltViewModel()
    val currentShoppingCartItemCount by viewModel.currentShoppingCartCount.collectAsStateWithLifecycle()
    val searchBarActive by viewModel.searchBarActive.collectAsStateWithLifecycle()
    val previousQueryList by viewModel.queryList.collectAsStateWithLifecycle()
    val itemShoppingList by viewModel.itemShoppingList.collectAsStateWithLifecycle()
    Screen {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        TextTitle(stringResource(R.string.addshoppingcart_scaffold_title))
                    },
                    navigationIcon = {
                        IconButton(onClick = { popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.backhome_icon_content_description)
                            )
                        }
                    },
                    actions = {
                        BadgedBox(
                            badge = {
                                if (currentShoppingCartItemCount > 0) {
                                    Badge {
                                        Text(
                                            currentShoppingCartItemCount.toString(),
                                            modifier = Modifier.semantics {
                                                contentDescription = "$currentShoppingCartItemCount items"
                                            }
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(end = MEDIUM)
                        ) {
                            IconButton(
                                onClick = {},
                                enabled = currentShoppingCartItemCount > 0
                            ) {
                                Icon(
                                    Icons.Filled.ShoppingBasket,
                                    contentDescription = stringResource(R.string.shoppingbasket_icon_content_description)
                                )
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                if (!searchBarActive) {
                    AddShoppingCartFAB {
                        navigateToNewItem()
                    }
                }
            }
        ) {
            AddShoppingCartScreen(
                searchBarQueryValue = viewModel.searchInputQuery.collectAsStateWithLifecycle().value,
                viewModel::searchInputChange,
                viewModel::searchShoppingProductWithCurrentValue,
                searchBarActive,
                viewModel::changeInputActive,
                viewModel::removeInputQueryClicked,
                previousQueryList,
                viewModel::removeQuery,
                itemShoppingList,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddShoppingCartScreen(
    searchBarQueryValue: String,
    searchInputChange: (String) -> Unit,
    searchShoppingProductWithCurrentValue: (String) -> Unit,
    isSearchBarActive: Boolean,
    changeInputActive: (Boolean) -> Unit,
    removeInputQueryClicked: () -> Unit,
    previousQueryList: List<String>,
    removeQuery: (String) -> Unit,
    itemShoppingList: List<ShoppingItem>,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (searchBar, emptyItemList, shoppingList) = createRefs()
        SearchBar(
            query = searchBarQueryValue,
            onQueryChange = searchInputChange,
            onSearch = searchShoppingProductWithCurrentValue,
            active = isSearchBarActive,
            onActiveChange = changeInputActive,
            placeholder = {
                Text(stringResource(R.string.seachbar_placeholder_text))
            },
            trailingIcon = {
                IconButton(onClick = removeInputQueryClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Backspace,
                        contentDescription = stringResource(R.string.clear_searchbar_content_description_icon)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LOW)
                .constrainAs(searchBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            if (previousQueryList.isEmpty()) {
                EmptyQueryList(
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                PreviousQuery(
                    previousQueryList,
                    searchShoppingProductWithCurrentValue,
                    removeQuery
                )
            }
        }
        if (itemShoppingList.isEmpty()) {
            EmptyShoppingItemList(
                modifier = Modifier
                    .padding(MEDIUM)
                    .constrainAs(emptyItemList) {
                        top.linkTo(searchBar.bottom, LOW)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        } else {
            ItemShoppinListScreen(
                itemShoppingList = itemShoppingList,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(shoppingList) {
                        top.linkTo(searchBar.bottom, MEDIUM)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onItemClicked = { itemClicked ->

                }
            )
        }
    }
}

@Composable
fun PreviousQuery(
    queryList: List<String>,
    onSelectQuery: (String) -> Unit,
    onDeleteQueryClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(LOW),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = queryList.size,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = queryList[it],
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, false)
                        .clickable {
                            onSelectQuery(queryList[it])
                        },
                    fontSize = 18.sp
                )

                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.remove_query_icon_content_description),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onDeleteQueryClicked(queryList[it])
                        },
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun EmptyShoppingItemList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = stringResource(R.string.empty_shopping_item_list_icon_content_description),
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(MEDIUM))
        Text(
            text = stringResource(R.string.empty_item_list_text),
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmptyQueryList(
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(R.string.empty_query_list_icon_content_description),
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(MEDIUM))
        Text(
            text = stringResource(R.string.empty_query_list_text),
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AddShoppingCartFAB(
    onClickFab: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClickFab
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_item_fab_text_value),
            modifier = Modifier
                .padding(end = LOW)
        )
        TextSemiBold(
            stringResource(R.string.add_item_fab_text_value),
            textSize = TextSmallSize
        )
    }
}

@Composable
fun ItemShoppinListScreen(
    itemShoppingList: List<ShoppingItem>,
    onItemClicked: (ShoppingItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)
    ) {
        items(
            count = itemShoppingList.size
        ) { index ->
            ItemShopping(
                itemShoppingList[index],
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onItemClicked(itemShoppingList[index])
                    }
            )
        }
    }
}

@Composable
fun ItemShopping(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
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
                    .size(100.dp),
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Box(
                        modifier = Modifier
                            .background(Color.Cyan)
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
        Text(
            text = item.name
        )
    }
}

fun shoppingItemListMocked(): List<ShoppingItem> = listOf(
    ShoppingItem(
        name = "Manzana",
        imageUrl = "https://cdn.icon-icons.com/icons2/16/PNG/256/fruit_apple_food_1815.png"
    ), ShoppingItem(
        name = "Pera",
        imageUrl = "https://cdn.icon-icons.com/icons2/16/PNG/256/fruit_apple_food_1815.png"
    )
)

@PIXEL_33_NIGHT
@Composable
fun AddShoppingCartPrev() {
    Screen {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                AddShoppingCartFAB { }
            }
        ) {
            AddShoppingCartScreen(
                searchBarQueryValue = "",
                searchInputChange = {},
                searchShoppingProductWithCurrentValue = {},
                isSearchBarActive = false,
                changeInputActive = {},
                removeInputQueryClicked = {},
                previousQueryList = listOf(
                    "Manzana", "Pera"
                ),
                removeQuery = {},
                itemShoppingList = emptyList(),
                modifier = Modifier
                    .padding(it)
            )
        }
    }
}