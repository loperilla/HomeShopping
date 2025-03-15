package io.loperilla.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.TransparentScaffold
import io.loperilla.designsystem.composables.button.SimpleButton
import io.loperilla.designsystem.composables.image.UriImage
import io.loperilla.designsystem.composables.input.TextInput
import io.loperilla.designsystem.composables.spacers.FullWeightSpacer
import io.loperilla.designsystem.composables.spacers.LowSpacer
import io.loperilla.designsystem.composables.topbar.CommonTopBar
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.domain.model.dummy.dummyUser

/*****
 * Project: HomeShopping
 * From: io.loperilla.presentation
 * Created By Manuel Lopera on 9/3/25 at 17:35
 * All rights reserved 2025
 */

@Composable
fun UserDetailScreen(
    state: UserDetailState,
    onEvent: (UserDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    TransparentScaffold(
        topBar = {
            if (!state.isLoading) {
                CommonTopBar(
                    topBarText = "Usuario",
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    navActionClick = {
                        onEvent(UserDetailEvent.OnBackPressed)
                    }
                )
            }
        }
    ) { paddingValues ->
        AnimatedVisibility(state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        AnimatedVisibility(!state.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                state.user?.let { user ->
                    UriImage(
                        uriImage = state.userImageUrlUri,
                        imageName = "user_image",
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary
                            )
                    )

                    TextInput(
                        text = state.newUserName,
                        labelText = "Nombre",
                        onTextChange = { newName ->
                            onEvent(UserDetailEvent.OnNameChanged(newName))
                        }
                    )

                    FullWeightSpacer()

                    SimpleButton(
                        textButton = "Guardar",
                        enableButton = state.validForm,
                        onClickButton = {
                            onEvent(UserDetailEvent.SaveButtonClick)
                        }
                    )
                    LowSpacer()
                }
            }
        }
    }
}

@PIXEL_33_NIGHT
@Composable
private fun UserDetailPrevScreen() {
    Screen {
        UserDetailScreen(
            state = UserDetailState(
                user = dummyUser
            ),
            onEvent = {}
        )
    }
}