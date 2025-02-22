package io.loperilla.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.loperilla.designsystem.composables.Screen
import io.loperilla.designsystem.composables.button.ShoppingButton
import io.loperilla.designsystem.composables.spacers.FullWeightSpacer
import io.loperilla.designsystem.previews.PIXEL_33_NIGHT
import io.loperilla.presentation.designsystem.R

/*****
 * Project: HomeShopping
 * From: io.loperilla.splash.presentation
 * Created By Manuel Lopera on 9/2/25 at 12:43
 * All rights reserved 2025
 */

@Composable
fun WelcomeScreen(
    onEvent: (WelcomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        FullWeightSpacer()
        Image(
            painter = painterResource(R.mipmap.brand),
            contentDescription = "Application logo",
            modifier = modifier
                .wrapContentSize()
                .clip(CircleShape)
        )
        FullWeightSpacer()
        ShoppingButton(
            textButton = "Iniciar Sesión",
            onClickButton = {
                onEvent(WelcomeEvent.NavigateToLogin)
            },
            enableButton = true
        )

        ShoppingButton(
            textButton = "Registrarse",
            onClickButton = {
                onEvent(WelcomeEvent.NavigateToRegister)
            },
            enableButton = true
        )
        FullWeightSpacer()
    }
}

@PIXEL_33_NIGHT
@Composable
private fun WelcomePreview() {
    Screen {
        WelcomeScreen(
            onEvent = {}
        )
    }
}