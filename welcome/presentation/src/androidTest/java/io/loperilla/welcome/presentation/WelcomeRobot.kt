package io.loperilla.welcome.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.loperilla.splash.presentation.WelcomeEvent
import io.loperilla.splash.presentation.WelcomeScreen
import io.loperilla.testing.robot.Robot
import junit.framework.TestCase.assertEquals
import org.junit.Assert

/*****
 * Project: HomeShopping
 * From: io.loperilla.welcome.presentation
 * Created By Manuel Lopera on 2/12/25 at 20:19
 * All rights reserved 2025
 */
class WelcomeScreenRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
) : Robot(composeTestRule) {
    private var capturedEvent: WelcomeEvent? = null
    fun setUpRobot(): WelcomeScreenRobot {
        composeTestRule.setContent {
            WelcomeScreen(
                onEvent = { event ->
                    capturedEvent = event
                }
            )
        }
        capturedEvent = null
        return this
    }
    fun assertExpectedEvent(expectedEvent: WelcomeEvent): WelcomeScreenRobot {
        assertEquals(expectedEvent, capturedEvent)
        return this
    }
}