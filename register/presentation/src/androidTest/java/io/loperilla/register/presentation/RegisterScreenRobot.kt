package io.loperilla.register.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.loperilla.presentation.RegisterEvent
import io.loperilla.presentation.RegisterScreen
import io.loperilla.presentation.RegisterState
import io.loperilla.testing.robot.Robot
import io.loperilla.testing.robot.assertTagIsEnabled
import io.loperilla.testing.tag.RegisterTag
import org.junit.Assert


/*****
 * Project: HomeShopping
 * From: io.loperilla.login.presentation
 * Created By Manuel Lopera on 30/11/25 at 15:11
 * All rights reserved 2025
 */
class RegisterScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
): Robot(composeTestRule) {
    private val capturedEvents = mutableListOf<RegisterEvent>()

    fun setUpRobot(
        state: RegisterState = RegisterState()
    ): RegisterScreenRobot {
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(state) }

            RegisterScreen(
                state = uiState,
                onEvent = { event ->
                    capturedEvents.add(event)
                    uiState = when (event) {
                        is RegisterEvent.EmailValueChange -> uiState.copy(email = event.emailValue)
                        is RegisterEvent.PasswordValueChange -> uiState.copy(password = event.passwordValue)
                        is RegisterEvent.RepeatPasswordValueChange -> uiState.copy(confirmPassword = event.repeatPasswordValue)
                        else -> uiState
                    }
                }
            )
        }
        capturedEvents.clear()
        return this
    }

    fun assertExpectedEvent(expectedEvent: RegisterEvent): RegisterScreenRobot {
        Assert.assertEquals(expectedEvent, capturedEvents.last())
        return this
    }

    fun assertFormValid(): RegisterScreenRobot {
        this.assertTagIsEnabled(RegisterTag.RegisterButton)
        return this
    }
}