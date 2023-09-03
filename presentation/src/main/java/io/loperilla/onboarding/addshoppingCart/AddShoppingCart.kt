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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.LOW
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.input.TextInput
import io.loperilla.core_ui.input.UrlInput
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.routes.Routes
import io.loperilla.model.database.ShoppingItem
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
    newDestination: (String) -> Unit
) {
    val viewModel: AddShoppingCartViewModel = hiltViewModel()
    val currentShoppingCartItemCount by viewModel.currentShoppingCartCount.collectAsStateWithLifecycle()
    val searchBarActive by viewModel.searchBarActive.collectAsStateWithLifecycle()
    val previousQueryList by viewModel.queryList.collectAsStateWithLifecycle()
    val itemShoppingList by viewModel.itemShoppingList.collectAsStateWithLifecycle()
    val isFabClicked by viewModel.isAddItemFABClicked.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Add ShoppingCart")
                },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver al Home"
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
                            onClick = {}, enabled = currentShoppingCartItemCount > 0
                        ) {
                            Icon(
                                Icons.Filled.ShoppingBasket,
                                contentDescription = "Contenido del nuevo carrito"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (!searchBarActive) {
                AddShoppingCartFAB {
                    newDestination(Routes.NEW_ITEM.route)
                }
            }
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (searchBar, emptyItemList, shoppingList) = createRefs()
            if (isFabClicked) {
                AddShoppingItemDialog(
                    dismissRequest = viewModel::closeDialog,
                    onFinishCreate = viewModel::createItem
                )
            }
            SearchBar(
                query = viewModel.searchInputQuery.collectAsStateWithLifecycle().value,
                onQueryChange = viewModel::searchInputChange,
                onSearch = viewModel::searchShoppingProductWithCurrentValue,
                active = searchBarActive,
                onActiveChange = viewModel::changeInputActive,
                placeholder = {
                    Text("Busca tu producto aquí")
                },
                trailingIcon = {
                    IconButton(onClick = viewModel::removeInputQueryClicked) {
                        Icon(
                            imageVector = Icons.Outlined.Backspace,
                            contentDescription = "Limpiar searchBar"
                        )
                    }
                },
                modifier = Modifier
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
                        viewModel::searchShoppingProductWithCurrentValue,
                        viewModel::removeQuery
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
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(shoppingList) {
                            top.linkTo(searchBar.bottom, MEDIUM)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    itemShoppingList
                ) { itemClicked ->
//                viewModel.itemWasSelected(itemClicked)
                }
            }
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
                    contentDescription = "Eliminar query",
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
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "Carrito de la compra vacío",
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(MEDIUM))
        Text(
            text = "No hay Items registrados",
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
            contentDescription = "Carrito de la compra vacío",
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(MEDIUM))
        Text(
            text = "Tus búsquedas estarán aquí",
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
            imageVector = Icons.Filled.Add, contentDescription = "Add",
            modifier = Modifier
                .padding(end = LOW)
        )
        Text("Agregar producto")
    }
}

@Composable
fun ItemShoppinListScreen(
    modifier: Modifier,
    itemShoppingList: List<ShoppingItem>,
    onItemClicked: (ShoppingItem) -> Unit
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
fun AddShoppingItemDialog(
    dismissRequest: () -> Unit,
    onFinishCreate: (String, String) -> Unit
) {
    var nameInput by remember { mutableStateOf("") }
    var nameInputValid by remember { mutableStateOf(false) }
    var urlInput by remember { mutableStateOf("") }
    var urlInputValid by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = dismissRequest,
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(MEDIUM),
            shape = RoundedCornerShape(MEDIUM)
        ) {
            UrlInput(
                urlInput,
                modifier = Modifier
                    .padding(LOW)
            ) { newInputValue, isValid ->
                urlInput = newInputValue
                urlInputValid = isValid
            }

            TextInput(
                nameInput,
                modifier = Modifier
                    .padding(LOW)
            ) { newInputValue, isValid ->
                nameInput = newInputValue
                nameInputValid = isValid
            }

            Button(
                onClick = {
                    onFinishCreate(nameInput, urlInput)
                },
                enabled = nameInputValid && urlInputValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LOW)
            ) {
                Text("Crear Item")
            }
        }
    }
}

@Composable
fun ItemShopping(
    item: ShoppingItem,
    modifier: Modifier,
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

@PIXEL_33_NIGHT
@Composable
fun AddShoppingCartPrev() {
    HomeShoppingTheme {
        Surface {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                floatingActionButton = {
                    AddShoppingCartFAB { }
                }
            ) {
                EmptyQueryList(
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}