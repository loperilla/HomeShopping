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

        // Verificamos que la imagen del logo exista
        composeTestRule
            .onNodeWithContentDescription("Application logo")
            .assertIsDisplayed()

        // Verificamos el botón de Iniciar Sesión
        composeTestRule
            .onNodeWithText("Iniciar Sesión")
            .assertIsDisplayed()

        // Verificamos el botón de Registrarse
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

        // Buscamos por content description y verificamos que sea correcto (accesibilidad)
        composeTestRule
            .onNodeWithContentDescription("Application logo")
            .assertContentDescriptionEquals("Application logo")
    }

    @Test
    fun navigateToLoginEventTrigger() {
        var capturedEvent: WelcomeEvent? = null

        composeTestRule.setContent {
            // Capturamos el evento en la lambda
            WelcomeScreen(onEvent = { event ->
                capturedEvent = event
            })
        }

        // Hacemos click en el botón
        composeTestRule
            .onNodeWithText("Iniciar Sesión")
            .performClick()

        // Verificamos que el evento capturado sea el correcto
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

        // Hacemos click en el botón
        composeTestRule
            .onNodeWithText("Registrarse")
            .performClick()

        // Verificamos que el evento capturado sea el correcto
        assertEquals(WelcomeEvent.NavigateToRegister, capturedEvent)
    }

    @Test
    fun buttonsAreEnabled() {
        composeTestRule.setContent {
            WelcomeScreen(onEvent = {})
        }

        // Verificamos que ambos botones tengan la acción de click y estén habilitados
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
