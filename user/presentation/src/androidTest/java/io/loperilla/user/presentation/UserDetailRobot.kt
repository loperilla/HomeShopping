package io.loperilla.user.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.loperilla.presentation.UserDetailEvent
import io.loperilla.presentation.UserDetailScreen
import io.loperilla.presentation.UserDetailState
import io.loperilla.testing.robot.Robot
import io.loperilla.testing.robot.assertTagIsEnabled
import io.loperilla.testing.tag.UserDetailTag
import org.junit.Assert

/*****
 * Project: HomeShopping
 * From: io.loperilla.user.presentation
 * Created By Manuel Lopera on 26/12/25 at 19:53
 * All rights reserved 2025
 */
class UserDetailRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) : Robot(composeTestRule) {
    private val capturedEvents = mutableListOf<UserDetailEvent>()

    fun setUpRobot(
        state: UserDetailState = UserDetailState()
    ): UserDetailRobot {
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(state) }

            UserDetailScreen(
                state = uiState,
                onEvent = { event ->
                    capturedEvents.add(event)
                    uiState = when (event) {
                        is UserDetailEvent.OnNameChanged -> uiState.copy(newUserName = event.newName)
                        else -> uiState
                    }
                }
            )
        }
        capturedEvents.clear()
        return this
    }

    fun assertExpectedEvent(expectedEvent: UserDetailEvent): UserDetailRobot {
        Assert.assertEquals(expectedEvent, capturedEvents.last())
        return this
    }

    fun assertSaveButtonEnabled(): UserDetailRobot {
        this.assertTagIsEnabled(UserDetailTag.UserDetailSaveButton)
        return this
    }
}
