package io.loperilla.welcome.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.loperilla.splash.presentation.WelcomeEvent
import io.loperilla.splash.presentation.WelcomeScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verifyUIStructureAndComponentsPresence() {
        composeTestRule.setContent {
            WelcomeScreen(onEvent = {})
        }

        composeTestRule
            .onNodeWithContentDescription("Application logo")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Iniciar Sesión")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Registrarse")
            .assertIsDisplayed()
    }

    @Test
    fun verifyLoginButtonText() {
        composeTestRule.setContent {
            WelcomeScreen(onEvent = {})
        }

        composeTestRule
            .onNodeWithText("Iniciar Sesión")
            .assertTextEquals("Iniciar Sesión")
    }

    @Test
    fun verifyRegisterButtonText() {
        composeTestRule.setContent {
            WelcomeScreen(onEvent = {})
        }

        composeTestRule
            .onNodeWithText("Registrarse")
            .assertTextEquals("Registrarse")
    }

    @Test
    fun verifyImageContentDescription() {
        composeTestRule.setContent {
            WelcomeScreen(onEvent = {})
        }

        composeTestRule
            .onNodeWithContentDescription("Application logo")
            .assertContentDescriptionEquals("Application logo")
    }

    @Test
    fun navigateToLoginEventTrigger() {
        var capturedEvent: WelcomeEvent? = null

        composeTestRule.setContent {
            WelcomeScreen(onEvent = { event ->
                capturedEvent = event
            })
        }

        composeTestRule
            .onNodeWithText("Iniciar Sesión")
            .performClick()

        assertEquals(WelcomeEvent.NavigateToLogin, capturedEvent)
    }

    @Test
    fun navigateToRegisterEventTrigger() {
        var capturedEvent: WelcomeEvent? = null

        composeTestRule.setContent {
            WelcomeScreen(onEvent = { event ->
                capturedEvent = event
            })
        }

        composeTestRule
            .onNodeWithText("Registrarse")
            .performClick()

        assertEquals(WelcomeEvent.NavigateToRegister, capturedEvent)
    }

    @Test
    fun buttonsAreEnabled() {
        composeTestRule.setContent {
            WelcomeScreen(onEvent = {})
        }

        composeTestRule
            .onNodeWithText("Iniciar Sesión")
            .assertHasClickAction()
            .assertIsEnabled()

        composeTestRule
            .onNodeWithText("Registrarse")
            .assertHasClickAction()
            .assertIsEnabled()
    }
}
