package io.loperilla.testing.robot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.loperilla.testing.tag.Tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.robot
 * Created By Manuel Lopera on 2/12/25 at 19:03
 * All rights reserved 2025
 */

abstract class Robot(
    internal val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>
)

fun <T : Robot> T.assertTagIsDisplayed(tag: Tag): T {
    composeTestRule
        .onNodeWithTag(tag.name)
        .assertExists("${tag.name} no encontrado")
        .assertIsDisplayed()
    return this // 'this' aquí es T, sin cast inseguros.
}

fun <T : Robot> T.assertTagIsEnabled(tag: Tag): T {
    composeTestRule
        .onNodeWithTag(tag.name)
        .assertExists("${tag.name} no encontrado")
        .assertIsEnabled()
    return this
}

fun <T : Robot> T.assertTagIsNotEnabled(tag: Tag): T {
    composeTestRule
        .onNodeWithTag(tag.name)
        .assertExists("${tag.name} no encontrado")
        .assertIsNotEnabled()
    return this
}

fun <T : Robot> T.performClickOnTag(tag: Tag): T {
    composeTestRule
        .onNodeWithTag(tag.name)
        .assertIsEnabled()
        .performClick()
    return this
}

fun <T : Robot> T.performTextInputToTag(tag: Tag, text: String): T {
    composeTestRule
        .onNodeWithTag(tag.name)
        .performTextInput(text)
    return this
}

fun <T : Robot> T.performTextInputImeActionToTag(tag: Tag): T {
    composeTestRule
        .onNodeWithTag(tag.name)
        .performImeAction()
    return this
}

