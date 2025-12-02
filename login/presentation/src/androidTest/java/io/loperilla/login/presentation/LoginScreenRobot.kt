package io.loperilla.login.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.loperilla.presentation.LoginEvent
import io.loperilla.presentation.LoginScreen
import io.loperilla.presentation.LoginState
import io.loperilla.testing.robot.Robot
import org.junit.Assert

/*****
 * Project: HomeShopping
 * From: io.loperilla.login.presentation
 * Created By Manuel Lopera on 2/12/25 at 20:40
 * All rights reserved 2025
 */
class LoginScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
): Robot(composeTestRule) {
    private var capturedEvent: LoginEvent? = null

    fun setUpRobot(
        state: LoginState = LoginState()
    ): LoginScreenRobot {
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(state) }

            LoginScreen(
                state = uiState,
                onEvent = { event ->
                    capturedEvent = event
                    uiState = when(event) {
                        is LoginEvent.EmailValueChange -> uiState.copy(emailInputValue = event.newEmailValue)
                        is LoginEvent.PasswordValueChange -> uiState.copy(passwordInputValue = event.newPasswordValue)
                        else -> uiState
                    }
                }
            )
        }
        capturedEvent = null
        return this
    }

    fun assertExpectedEvent(expectedEvent: LoginEvent): LoginScreenRobot {
        Assert.assertEquals(expectedEvent, capturedEvent)
        return this
    }
}