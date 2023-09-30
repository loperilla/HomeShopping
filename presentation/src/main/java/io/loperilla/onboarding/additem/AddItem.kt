package io.loperilla.onboarding.additem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.pagerTabIndicatorOffset
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.alert_dialog.CommonAlertDialog
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.TextInput
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.spacers.LowSpacer
import io.loperilla.core_ui.spinner.Spinner
import io.loperilla.core_ui.tab.TabRowItem
import io.loperilla.core_ui.text.TextSemiBold
import io.loperilla.core_ui.text.TextTitle
import io.loperilla.model.database.Commerce
import io.loperilla.onboarding.additem.camera.AddCameraImageScreen
import io.loperilla.onboarding.additem.storage.AddStorageImageScreen
import io.loperilla.onboarding_presentation.R
import kotlinx.coroutines.launch
import timber.log.Timber

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 1/9/23 at 18:11
 * All rights reserved 2023
 */

@Composable
fun AddItemScreen(
    popBackStack: () -> Unit
) {
    val viewModel: AddItemViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val commerceList by viewModel.commerceList.collectAsStateWithLifecycle()

    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(R.string.tab_camera_text)
        ) {
            AddCameraImageScreen(
                viewModel::onEvent
            )
        },
        TabRowItem(
            title = stringResource(R.string.tab_storage_text)
        ) {
            AddStorageImageScreen(viewModel::onEvent)
        }
    )
    if (state.addItemRequestState == AddItemRequestState.SUCCESS) {
        popBackStack()
        return
    }

    Screen {
        AddItem(
            popBackStack,
            tabRowItems,
            state,
            commerceList,
            viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddItem(
    popBackStack: () -> Unit,
    tabRowItems: List<TabRowItem>,
    state: AddItemState,
    commerceList: List<Commerce>,
    onEvent: (AddItemEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    if (state.addCommerceClicked) {
        AddCommerceDialog(
            onEvent
        )
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        tabRowItems.size
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TextTitle(stringResource(R.string.add_item_scaffold_title))
                },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.add_item_back_createbasket_content_description)
                        )
                    }
                }
            )
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val (
                tabRow,
                inputName,
                commerceSpinner,
                button
            ) = createRefs()

            Card(
                shape = RoundedCornerShape(MEDIUM),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM)
                    .fillMaxSize(0.5f)
                    .constrainAs(tabRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    backgroundColor = MaterialTheme.colorScheme.onSurface
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            enabled = state.isPagerEnabled,
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            text = {
                                TextSemiBold(
                                    text = item.title,
                                    textColor = Color.White,
                                    textSize = TextSmallSize
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    userScrollEnabled = state.isPagerEnabled,
                    state = pagerState,
                ) {
                    tabRowItems[pagerState.currentPage].screen()
                }
            }
            TextInput(
                state.productName,
                onValueChange = { newValue, isValid ->
                    onEvent(AddItemEvent.ProductNameChanged(newValue))
                },
                labelText = stringResource(R.string.add_product_input_label),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM)
                    .constrainAs(inputName) {
                        top.linkTo(tabRow.bottom, MEDIUM)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            CommerceSpinner(
                state.isDropdownExpanded,
                commerceList.map { commerce ->
                    commerce.name
                },
                onEvent,
                modifier = Modifier
                    .padding(horizontal = MEDIUM)
                    .constrainAs(commerceSpinner) {
                        top.linkTo(inputName.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            FormButton(
                stringResource(R.string.add_product),
                enableButton = state.productName.isNotEmpty(),
                onClickButton = {
                    onEvent(AddItemEvent.AddProductButtonClicked)
                },
                modifier = Modifier
                    .padding(MEDIUM)
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
fun CommerceSpinner(
    isDropdownExpanded: Boolean,
    dropdownItems: List<String>,
    onEvent: (AddItemEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextSemiBold(
            "¿Dónde quieres comprar tu producto?",
            textSize = TextSmallSize
        )
        LowSpacer()
        Spinner(
            isExpanded = isDropdownExpanded,
            dropdownItems = dropdownItems,
            newItemSelected = { indexSelected ->
                Timber.tag("spinnerSelection").i("$indexSelected")
            },
            changeExpandedEvent = { newExpandedValue ->
                onEvent(AddItemEvent.NewDropdownExpandedState(newExpandedValue))
            },
            trailingIconOnClick = {
                onEvent(AddItemEvent.ShowAddCommerce)
            }
        )
    }
}

@Composable
fun AddCommerceDialog(
    onEvent: (AddItemEvent) -> Unit
) {
    CommonAlertDialog(
        alertIcon = Icons.Filled.AddBusiness,
        alertTitle = stringResource(R.string.add_commerce),
        acceptButtonText = stringResource(R.string.create),
        cancelButtonText = stringResource(R.string.logout_dialog_cancel_button),
        acceptButtonAction = { input ->
            onEvent(AddItemEvent.CreateNewCommerce(input))
        },
        dismissRequest = {
            onEvent(AddItemEvent.DismissNewCommerceDialog)
        }
    )
}

@PIXEL_33_NIGHT
@Composable
fun AddItemPrev() {
    Screen {
        AddItem(
            popBackStack = {},
            tabRowItems = listOf(
                TabRowItem(
                    title = stringResource(R.string.tab_camera_text)
                ) {
                    TextSemiBold("Soy Camera")
                },
                TabRowItem(
                    title = stringResource(R.string.tab_storage_text)
                ) {
                    TextSemiBold("Soy Storage")
                }
            ),
            state = AddItemState(),
            commerceList = listOf()
        ) {}
    }
}
