package io.loperilla.user.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.loperilla.domain.model.dummy.dummyUser
import io.loperilla.presentation.UserDetailEvent
import io.loperilla.presentation.UserDetailState
import io.loperilla.testing.robot.assertTagIsDisplayed
import io.loperilla.testing.robot.assertTagIsNotDisplayed
import io.loperilla.testing.robot.assertTagIsNotEnabled
import io.loperilla.testing.robot.performClickOnTag
import io.loperilla.testing.robot.performTextInputToTag
import io.loperilla.testing.tag.UserDetailTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.user.presentation
 * Created By Manuel Lopera on 12/12/25 at 19:07
 * All rights reserved 2025
 */
class UserDetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var robot: UserDetailRobot

    @Before
    fun setUp() {
        robot = UserDetailRobot(composeTestRule)
    }

    @Test
    fun initial_ui_when_loading_shows_progress_bar() {
        robot
            .setUpRobot(state = UserDetailState(isLoading = true))
            .assertTagIsDisplayed(UserDetailTag.UserDetailLoading)
    }

    @Test
    fun initial_ui_when_data_loaded_is_rendered() {
        robot
            .setUpRobot(state = UserDetailState(isLoading = false, user = dummyUser))
            .assertTagIsNotDisplayed(UserDetailTag.UserDetailLoading)
            .assertTagIsDisplayed(UserDetailTag.UserDetailRootTag)
            .assertTagIsDisplayed(UserDetailTag.UserDetailTopBar)
            .assertTagIsDisplayed(UserDetailTag.UserDetailImage)
            .assertTagIsDisplayed(UserDetailTag.UserDetailNameInput)
            .assertTagIsDisplayed(UserDetailTag.UserDetailSaveButton)
            .assertTagIsNotEnabled(UserDetailTag.UserDetailSaveButton)
    }

    @Test
    fun on_name_changed_event_is_triggered() {
        val newName = "Manolo"
        robot
            .setUpRobot(state = UserDetailState(isLoading = false, user = dummyUser))
            .performTextInputToTag(UserDetailTag.UserDetailNameInput, newName)
            .assertExpectedEvent(UserDetailEvent.OnNameChanged(newName))
    }

    @Test
    fun save_button_is_enabled_when_form_is_valid() {
        val newName = "Manolo"
        robot
            .setUpRobot(state = UserDetailState(isLoading = false, user = dummyUser))
            .assertTagIsNotEnabled(UserDetailTag.UserDetailSaveButton)
            .performTextInputToTag(UserDetailTag.UserDetailNameInput, newName)
            .assertSaveButtonEnabled()
    }

    @Test
    fun back_press_triggers_event() {
        robot
            .setUpRobot(state = UserDetailState(isLoading = false, user = dummyUser))
            .performClickOnTag(UserDetailTag.UserDetailTopBar)
            .assertExpectedEvent(UserDetailEvent.OnBackPressed)
    }

    @Test
    fun save_button_click_triggers_event() {
        val newName = "Manolo"
        robot
            .setUpRobot(
                state = UserDetailState(
                    isLoading = false,
                    user = dummyUser,
                    newUserName = newName
                )
            )
            .performClickOnTag(UserDetailTag.UserDetailSaveButton)
            .assertExpectedEvent(UserDetailEvent.SaveButtonClick)
    }
}
