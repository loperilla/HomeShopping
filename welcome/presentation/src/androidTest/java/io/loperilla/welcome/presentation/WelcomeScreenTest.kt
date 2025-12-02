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
import io.loperilla.testing.robot.assertTagContentDescriptionEquals
import io.loperilla.testing.robot.assertTagIsClickable
import io.loperilla.testing.robot.assertTagIsDisplayed
import io.loperilla.testing.robot.assertTagIsEnabled
import io.loperilla.testing.robot.assertTagText
import io.loperilla.testing.robot.performClickOnTag
import io.loperilla.testing.tag.WelcomeTags
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    lateinit var welcomeRobot: WelcomeScreenRobot

    @Before
    fun setUp() {
        welcomeRobot = WelcomeScreenRobot(composeTestRule)
    }

    @Test
    fun verifyUIStructureAndComponentsPresence() {
        welcomeRobot
            .setUpRobot()
            .assertTagIsDisplayed(WelcomeTags.Logo)
            .assertTagIsDisplayed(WelcomeTags.LoginButton)
            .assertTagIsDisplayed(WelcomeTags.RegisterButton)
    }

    @Test
    fun verifyLoginButtonText() {
        welcomeRobot
            .setUpRobot()
            .assertTagText(WelcomeTags.LoginButton, "Iniciar Sesión")
    }

    @Test
    fun verifyRegisterButtonText() {
        welcomeRobot
            .setUpRobot()
            .assertTagText(WelcomeTags.RegisterButton, "Registrarse")
    }

    @Test
    fun verifyImageContentDescription() {
        welcomeRobot
            .setUpRobot()
            .assertTagContentDescriptionEquals(
                tag = WelcomeTags.Logo,
                expectedDescription = "Application logo"
            )
    }

    @Test
    fun navigateToLoginEventTrigger() {
        welcomeRobot
            .setUpRobot()
            .performClickOnTag(WelcomeTags.LoginButton)
            .assertExpectedEvent(WelcomeEvent.NavigateToLogin)
    }

    @Test
    fun navigateToRegisterEventTrigger() {
        welcomeRobot
            .setUpRobot()
            .performClickOnTag(WelcomeTags.RegisterButton)
            .assertExpectedEvent(WelcomeEvent.NavigateToRegister)
    }

    @Test
    fun buttonsAreEnabledAndClickable() {
        welcomeRobot
            .setUpRobot()
            .assertTagIsClickable(WelcomeTags.LoginButton)
            .assertTagIsEnabled(WelcomeTags.LoginButton)
            .assertTagIsClickable(WelcomeTags.RegisterButton)
            .assertTagIsEnabled(WelcomeTags.RegisterButton)
    }
}
