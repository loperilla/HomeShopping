package io.loperilla.login.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.loperilla.presentation.LoginEvent
import io.loperilla.presentation.LoginState
import io.loperilla.testing.robot.assertTagContentDescriptionEquals
import io.loperilla.testing.robot.assertTagIsDisplayed
import io.loperilla.testing.robot.assertTagIsEnabled
import io.loperilla.testing.robot.assertTagIsNotEnabled
import io.loperilla.testing.robot.assertTextExists
import io.loperilla.testing.robot.assertTextHasFocus
import io.loperilla.testing.robot.performClickOnTag
import io.loperilla.testing.robot.performTextInputImeActionToTag
import io.loperilla.testing.robot.performTextInputToTag
import io.loperilla.testing.tag.LoginTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var loginRobot: LoginScreenRobot

    @Before
    fun setUp() {
        loginRobot = LoginScreenRobot(composeTestRule)
    }

    @Test
    fun initial_ui_state_rendering() {
        loginRobot
            .setUpRobot()
            .assertTagIsDisplayed(LoginTags.Icon)
            .assertTagIsDisplayed(LoginTags.RegisterButton)
            .assertTagIsDisplayed(LoginTags.LoginButton)
            .assertTagIsNotEnabled(LoginTags.LoginButton)
    }

    @Test
    fun email_input_text_update() {
        val initialText = "init_email"
        val inputText = "@test.com"
        val expectedResult = "$inputText$initialText"
        val state = LoginState(emailInputValue = initialText)

        loginRobot
            .setUpRobot(state)
            .performTextInputToTag(LoginTags.EmailInput, inputText)
            .assertExpectedEvent(LoginEvent.EmailValueChange(expectedResult))
    }

    @Test
    fun password_input_text_update() {
        val initialText = "init_pass"
        val inputText = "123"
        val state = LoginState(passwordInputValue = initialText)
        val expectedValue = "$inputText$initialText"

        loginRobot
            .setUpRobot(state)
            .performTextInputToTag(LoginTags.PasswordInput, inputText)
            .assertExpectedEvent(LoginEvent.PasswordValueChange(expectedValue))
    }

    @Test
    fun email_keyboard_next_action_focus_navigation() {
        val emailVal = "email_focus_test"
        val passVal = "pass_focus_test"
        val state = LoginState(emailInputValue = emailVal, passwordInputValue = passVal)

        loginRobot
            .setUpRobot(state)
            .performTextInputImeActionToTag(LoginTags.EmailInput)
            .assertTextHasFocus(passVal)
    }

    @Test
    fun password_keyboard_done_action_handling() {
        val passVal = "pass_done_test"
        val state = LoginState(passwordInputValue = passVal)

        loginRobot
            .setUpRobot(state)
            .performTextInputImeActionToTag(LoginTags.PasswordInput)
            .assertExpectedEvent(LoginEvent.LoginButtonClicked)
    }

    @Test
    fun login_button_enabled_state() {
        val validState = LoginState(
            emailInputValue = "valid@email.com",
            passwordInputValue = "Password123!",
        )

        loginRobot
            .setUpRobot(validState)
            .assertTagIsEnabled(LoginTags.LoginButton)
    }

    @Test
    fun login_button_disabled_state() {
        val invalidState = LoginState(
            emailInputValue = "invalid-email",
            passwordInputValue = "123",
        )

        loginRobot
            .setUpRobot(invalidState)
            .assertTagIsNotEnabled(LoginTags.LoginButton)
    }

    @Test
    fun login_button_click_event() {
        val validState = LoginState(
            emailInputValue = "test@test.com",
            passwordInputValue = "123456",
        )

        loginRobot
            .setUpRobot(validState)
            .performClickOnTag(LoginTags.LoginButton)
            .assertExpectedEvent(LoginEvent.LoginButtonClicked)
    }

    @Test
    fun register_button_click_event() {
        loginRobot
            .setUpRobot()
            .performClickOnTag(LoginTags.RegisterButton)
            .assertExpectedEvent(LoginEvent.RegisterButtonClicked)
    }

    @Test
    fun empty_sate_edge_case() {
        val emptyState = LoginState(emailInputValue = "", passwordInputValue = "")

        loginRobot
            .setUpRobot(emptyState)
            .assertTagIsNotEnabled(LoginTags.LoginButton)
            .assertTagIsDisplayed(LoginTags.Icon)
    }

    @Test
    fun long_text_input_edge_case() {
        val longText = "a".repeat(500)
        val state = LoginState(emailInputValue = longText)

        loginRobot
            .setUpRobot(state)
            .assertTextExists(longText)
    }

    @Test
    fun logo_image_loading_and_accessibility() {
        loginRobot
            .setUpRobot()
            .assertTagContentDescriptionEquals(LoginTags.Icon, "Application logo")
    }
}
