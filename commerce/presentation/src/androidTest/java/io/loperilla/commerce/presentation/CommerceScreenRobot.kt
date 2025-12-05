package io.loperilla.commerce.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.loperilla.presentation.CommerceEvent
import io.loperilla.presentation.CommerceScreen
import io.loperilla.presentation.CommerceState
import io.loperilla.testing.robot.Robot
import io.loperilla.testing.tag.CommerceTags
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking

/*****
 * Project: HomeShopping
 * From: io.loperilla.commerce.presentation
 * Created By Manuel Lopera on 3/12/25 at 20:20
 * All rights reserved 2025
 */
class CommerceScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) : Robot(composeTestRule) {

    private var capturedEvent: CommerceEvent? = null

    fun setUpRobot(
        state: CommerceState = CommerceState()
    ): CommerceScreenRobot {
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(state) }

            CommerceScreen(
                state = uiState,
                onEvent = { event ->
                    capturedEvent = event
                    when(event) {
                        is CommerceEvent.NewCommerceNameChanged -> {
                            uiState = uiState.copy(newCommerceName = event.name)
                        }
                        else -> uiState
                    }
                }
            )
        }
        capturedEvent = null
        return this
    }

    fun assertCommerceItemDisplayed(name: String): CommerceScreenRobot {
        composeTestRule.onNodeWithText(name).assertIsDisplayed()
        return this
    }

    fun assertTopBarAndFabDoesNotExist(): CommerceScreenRobot {
        composeTestRule.onNodeWithTag(CommerceTags.TopBar.name).assertDoesNotExist()
        composeTestRule.onNodeWithTag(CommerceTags.Fab.name).assertDoesNotExist()
        return this
    }

    fun assertEmptyStateDoesNotExist(): CommerceScreenRobot {
        composeTestRule.onNodeWithTag(CommerceTags.EmptyList.name).assertDoesNotExist()
        return this
    }

    fun assertListDoesNotExist(): CommerceScreenRobot {
        composeTestRule.onNodeWithTag(CommerceTags.List.name).assertDoesNotExist()
        return this
    }

    fun assertExpectedEvent(expectedEvent: CommerceEvent): CommerceScreenRobot {
        assertEquals(expectedEvent, capturedEvent)
        return this
    }

    fun performSwipeToDelete(commerceName: String): CommerceScreenRobot {
        composeTestRule
            .onNodeWithText(commerceName)
            .performTouchInput {
                swipeLeft()
            }
        runBlocking {
            composeTestRule.awaitIdle()
        }
        return this
    }

    fun performClickOnTopBarBack(): CommerceScreenRobot {
        composeTestRule.onNode(
            hasClickAction() and hasAnyAncestor(hasTestTag(CommerceTags.TopBar.name))
        ).performClick()
        return this
    }
}

