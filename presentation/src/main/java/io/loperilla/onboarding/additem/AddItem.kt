package io.loperilla.onboarding.additem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.pagerTabIndicatorOffset
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.button.FormButton
import io.loperilla.core_ui.input.TextInput
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.tab.TabRowItem
import io.loperilla.onboarding.additem.camera.AddCameraImageScreen
import io.loperilla.onboarding.additem.storage.AddStorageImageScreen
import io.loperilla.onboarding_presentation.R
import kotlinx.coroutines.launch

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 1/9/23 at 18:11
 * All rights reserved 2023
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddItem(
    popBackStack: () -> Unit
) {
    val viewModel: AddItemViewModel = hiltViewModel()
    val pagerUserInputEnabled by viewModel.pagerUserInputEnabled.collectAsStateWithLifecycle()
    val inputValue by viewModel.inputNameValue.collectAsStateWithLifecycle()
    val isInputValid by viewModel.isInputValid.collectAsStateWithLifecycle()
    val isBitmapSelected by viewModel.bitmapSelected.collectAsStateWithLifecycle()
    val isInsertSuccess by viewModel.insertItemSuccess.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(R.string.tab_camera_text)
        ) {
            AddCameraImageScreen(
                viewModel::userGoesToTakeAPhoto,
                viewModel::userTakeAPhoto
            )
        },
        TabRowItem(
            title = stringResource(R.string.tab_storage_text)
        ) {
            AddStorageImageScreen(viewModel::userTakeAPhoto)
        }
    )

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
                    Text(stringResource(R.string.add_item_scaffold_title))
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
            if (isInsertSuccess) {
                popBackStack()
                return@ConstraintLayout
            }
            val (tabRow, inputName, button) = createRefs()

            Card(
                shape = RoundedCornerShape(MEDIUM),
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
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
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            enabled = pagerUserInputEnabled,
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            text = {
                                Text(
                                    text = item.title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    userScrollEnabled = pagerUserInputEnabled,
                    state = pagerState,
                ) {
                    tabRowItems[pagerState.currentPage].screen()
                }

            }
            TextInput(
                inputValue,
                onValueChange = viewModel::inputChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MEDIUM)
                    .constrainAs(inputName) {
                        top.linkTo(tabRow.bottom, MEDIUM)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            FormButton(
                "Agregar Producto",
                enableButton = isInputValid && isBitmapSelected != null,
                onClickButton = viewModel::addProduct,
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

@PIXEL_33_NIGHT
@Composable
fun AddItemPrev() {
    HomeShoppingTheme {
        Surface {
            AddItem {

            }
        }
    }
}
