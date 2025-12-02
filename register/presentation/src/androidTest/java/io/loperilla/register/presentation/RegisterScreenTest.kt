package io.loperilla.register.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.loperilla.presentation.RegisterEvent
import io.loperilla.presentation.RegisterState
import io.loperilla.testing.robot.assertTagIsDisplayed
import io.loperilla.testing.robot.assertTagIsNotEnabled
import io.loperilla.testing.robot.performClickOnTag
import io.loperilla.testing.robot.performTextInputImeActionToTag
import io.loperilla.testing.robot.performTextInputToTag
import io.loperilla.testing.tag.RegisterTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.register.presentation
 * Created By Manuel Lopera on 30/11/25 at 13:55
 * All rights reserved 2025
 */

class RegisterScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var registerScreenRobot: RegisterScreenRobot

    @Before
    fun setUp() {
        registerScreenRobot = RegisterScreenRobot(composeTestRule)
    }

    @Test
    fun initial_ui_register_screen_render() {
        registerScreenRobot
            .setUpRobot()
            .assertTagIsDisplayed(RegisterTag.RegisterRootTag)
            .assertTagIsDisplayed(RegisterTag.RegisterScreen)
            .assertTagIsDisplayed(RegisterTag.RegisterEmailInput)
            .assertTagIsDisplayed(RegisterTag.RegisterPasswordInput)
            .assertTagIsDisplayed(RegisterTag.RegisterRepeatPasswordInput)
            .assertTagIsDisplayed(RegisterTag.RegisterButton)
            .assertTagIsNotEnabled(RegisterTag.RegisterButton)
            .assertTagIsDisplayed(RegisterTag.RegisterGoogleButton)
    }

    @Test
    fun email_input_text_update() {
        val inputTest = "test123@test.com"
        registerScreenRobot
            .setUpRobot()
            .performTextInputToTag(RegisterTag.RegisterEmailInput, inputTest)
            .assertExpectedEvent(RegisterEvent.EmailValueChange(inputTest))
    }

    @Test
    fun password_input_text_update() {
        val inputText = "Password123!"
        registerScreenRobot
            .setUpRobot()
            .performTextInputToTag(RegisterTag.RegisterPasswordInput, inputText)
            .assertExpectedEvent(RegisterEvent.PasswordValueChange(inputText))

    }

    @Test
    fun repeat_password_input_text_update() {
        val inputText = "Password123!"
        registerScreenRobot
            .setUpRobot()
            .performTextInputToTag(RegisterTag.RegisterRepeatPasswordInput, inputText)
            .assertExpectedEvent(RegisterEvent.RepeatPasswordValueChange(inputText))
    }

    @Test
    fun register_button_enabled_state() {
        val emailTest = "test123@test.com"
        val passwordTest = "Password123!"

        registerScreenRobot
            .setUpRobot()
            .assertTagIsNotEnabled(RegisterTag.RegisterButton)
            .performTextInputToTag(RegisterTag.RegisterEmailInput, emailTest)
            .performTextInputImeActionToTag(RegisterTag.RegisterEmailInput)
            .performTextInputToTag(RegisterTag.RegisterPasswordInput, passwordTest)
            .performTextInputImeActionToTag(RegisterTag.RegisterPasswordInput)
            .performTextInputToTag(RegisterTag.RegisterRepeatPasswordInput, passwordTest)
            .assertFormValid()
    }

    @Test
    fun register_button_click_event() {
        val emailTest = "test123@test.com"
        val passwordTest = "Password123!"
        val stateTest = RegisterState(
            email = emailTest,
            password = passwordTest,
            confirmPassword = passwordTest
        )

        registerScreenRobot
            .setUpRobot(stateTest)
            .performClickOnTag(RegisterTag.RegisterButton)
    }
}