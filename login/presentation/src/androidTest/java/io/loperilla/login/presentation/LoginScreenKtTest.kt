package io.loperilla.login.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import io.loperilla.presentation.LoginEvent
import io.loperilla.presentation.LoginScreen
import io.loperilla.presentation.LoginState
import io.loperilla.testing.tag.LOGIN_BUTTON_TAG
import io.loperilla.testing.tag.LOGIN_EMAIL_INPUT
import io.loperilla.testing.tag.LOGIN_ICON_TAG
import io.loperilla.testing.tag.LOGIN_PASSWORD_INPUT
import io.loperilla.testing.tag.REGISTER_BUTTON_TAG
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun initial_ui_state_rendering() {
        composeTestRule.setContent {
            LoginScreen(state = LoginState(), onEvent = {})
        }

        composeTestRule
            .onNodeWithTag(LOGIN_ICON_TAG).assertExists()

        composeTestRule
            .onNodeWithTag(REGISTER_BUTTON_TAG).assertExists()

        composeTestRule
            .onNodeWithTag(LOGIN_BUTTON_TAG)
            .assertExists()
            .assertIsNotEnabled()
    }

    @Test
    fun email_input_text_update() {
        var capturedEvent: LoginEvent? = null
        val initialText = "init_email"
        val state = LoginState(emailInputValue = initialText)

        composeTestRule.setContent {
            LoginScreen(
                state = state,
                onEvent = {
                    capturedEvent = it
                }
            )
        }

        val inputText = "@test.com"
        composeTestRule
            .onNodeWithTag(LOGIN_EMAIL_INPUT)
            .performTextInput(inputText)

        val expectedResult = "$inputText$initialText"
        assertEquals(LoginEvent.EmailValueChange(expectedResult), capturedEvent)
    }

    @Test
    fun password_input_text_update() {
        var capturedEvent: LoginEvent? = null
        val initialText = "init_pass"
        val state = LoginState(passwordInputValue = initialText)

        composeTestRule.setContent {
            LoginScreen(
                state = state,
                onEvent = {
                    capturedEvent = it
                }
            )
        }

        val inputText = "123"

        composeTestRule
            .onNodeWithTag(LOGIN_PASSWORD_INPUT)
            .performTextInput(inputText)

        val expectedValue = "$inputText$initialText"
        assertEquals(LoginEvent.PasswordValueChange(expectedValue), capturedEvent)
    }

    @Test
    fun email_keyboard_next_action_focus_navigation() {
        val emailVal = "email_focus_test"
        val passVal = "pass_focus_test"
        val state = LoginState(emailInputValue = emailVal, passwordInputValue = passVal)

        composeTestRule.setContent {
            LoginScreen(
                state = state,
                onEvent = {}
            )
        }

        composeTestRule.onNodeWithText(emailVal).performImeAction()

        composeTestRule.onNodeWithText(passVal).assertIsFocused()
    }

    @Test
    fun password_keyboard_done_action_handling() {
        var capturedEvent: LoginEvent? = null
        val onEvent: (LoginEvent) -> Unit = { event ->
            capturedEvent = event
        }
        val passVal = "pass_done_test"
        val state = LoginState(passwordInputValue = passVal)

        composeTestRule.setContent {
            LoginScreen(
                state = state,
                onEvent = onEvent
            )
        }

        composeTestRule.onNodeWithText(passVal).performImeAction()

        assertEquals(LoginEvent.LoginButtonClicked, capturedEvent)
    }

    @Test
    fun login_button_enabled_state() {
        val validState = LoginState(
            emailInputValue = "valid@email.com",
            passwordInputValue = "Password123!"
        )

        composeTestRule.setContent {
            LoginScreen(state = validState, onEvent = {})
        }
        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsEnabled()
    }

    @Test
    fun login_button_disabled_state() {
        val invalidState = LoginState(
            emailInputValue = "invalid-email",
            passwordInputValue = "123"
        )

        composeTestRule.setContent {
            LoginScreen(state = invalidState, onEvent = {  })
        }

        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG).assertIsNotEnabled()
    }

    @Test
    fun login_button_click_event() {
        var capturedEvent: LoginEvent? = null
        val onEvent: (LoginEvent) -> Unit = { event ->
            capturedEvent = event
        }
        val validState = LoginState(
            emailInputValue = "test@test.com",
            passwordInputValue = "123456"
        )

        composeTestRule.setContent {
            LoginScreen(state = validState, onEvent = onEvent)
        }

        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG).performClick()

        if (validState.isValidForm) {
            assertEquals(LoginEvent.LoginButtonClicked, capturedEvent)
        }
    }

    @Test
    fun register_button_click_event() {
        var capturedEvent: LoginEvent? = null
        val onEvent: (LoginEvent) -> Unit = { event ->
            capturedEvent = event
        }
        composeTestRule.setContent {
            LoginScreen(state = LoginState(), onEvent = onEvent)
        }

        composeTestRule.onNodeWithTag(REGISTER_BUTTON_TAG).performClick()
        assertEquals(LoginEvent.RegisterButtonClicked, capturedEvent)
    }

    @Test
    fun empty_sate_edge_case() {
        val emptyState = LoginState(emailInputValue = "", passwordInputValue = "")

        composeTestRule.setContent {
            LoginScreen(state = emptyState, onEvent = {})
        }

        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG).assertIsNotEnabled()

        composeTestRule.onNodeWithTag(LOGIN_ICON_TAG).assertExists()
    }

    @Test
    fun long_text_input_edge_case() {
        val longText = "a".repeat(500)
        val state = LoginState(emailInputValue = longText)

        composeTestRule.setContent {
            LoginScreen(state = state, onEvent = { })
        }

        composeTestRule.onNodeWithText(longText).assertExists()
    }

    @Test
    fun logo_image_loading_and_accessibility() {
        composeTestRule.setContent {
            LoginScreen(state = LoginState(), onEvent = { })
        }

        composeTestRule.onNodeWithContentDescription("Application logo")
            .assertExists()
    }
}
