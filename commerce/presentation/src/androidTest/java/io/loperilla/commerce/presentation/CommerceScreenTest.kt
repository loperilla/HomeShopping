package io.loperilla.commerce.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.loperilla.domain.model.commerce.Commerce
import io.loperilla.presentation.CommerceEvent
import io.loperilla.presentation.CommerceState
import io.loperilla.testing.robot.assertTagIsDisplayed
import io.loperilla.testing.robot.performClickOnTag
import io.loperilla.testing.robot.performTextInputImeActionToTag
import io.loperilla.testing.robot.performTextInputToTag
import io.loperilla.testing.tag.CommerceTags
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.commerce.presentation
 * Created By Manuel Lopera on 30/11/25 at 12:18
 * All rights reserved 2025
 */

class CommerceScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var commerceRobot: CommerceScreenRobot

    @Before
    fun setUp() {
        commerceRobot = CommerceScreenRobot(composeTestRule)
    }

    @Test
    fun whenLoadingState_thenShowLoadingAndHideControls() {
        val state = CommerceState(isLoading = true)

        commerceRobot
            .setUpRobot(state)
            .assertTagIsDisplayed(CommerceTags.Loading)
            .assertTopBarAndFabDoesNotExist()
    }

    @Test
    fun whenDataLoaded_thenShowCommerceList() {
        val commerces = listOf(
            Commerce(id = "1", name = "Supermercado"),
            Commerce(id = "2", name = "Ferretería")
        )
        val state = CommerceState(
            isLoading = false,
            commerceList = commerces
        )

        commerceRobot
            .setUpRobot(state)
            .assertTagIsDisplayed(CommerceTags.TopBar)
            .assertTagIsDisplayed(CommerceTags.Fab)
            .assertTagIsDisplayed(CommerceTags.List)
            .assertCommerceItemDisplayed("Supermercado")
            .assertCommerceItemDisplayed("Ferretería")
            .assertEmptyStateDoesNotExist()
    }

    @Test
    fun whenListIsEmpty_thenShowEmptyState() {
        val state = CommerceState(
            isLoading = false,
            commerceList = emptyList()
        )

        commerceRobot
            .setUpRobot(state)
            .assertTagIsDisplayed(CommerceTags.EmptyList)
            .assertListDoesNotExist()
    }

    @Test
    fun whenClickOnBack_thenTriggerGoBackEvent() {
        val state = CommerceState(isLoading = false)

        commerceRobot
            .setUpRobot(state)
            .performClickOnTopBarBack()
            .assertExpectedEvent(CommerceEvent.GoBack)
    }

    @Test
    fun whenClickAddCommerceFAB_thenTriggerAddNewCommerceEvent() {
        val state = CommerceState(isLoading = false)

        commerceRobot
            .setUpRobot(state)
            .performClickOnTag(CommerceTags.Fab)
            .assertExpectedEvent(CommerceEvent.AddNewCommerce)
    }

    @Test
    fun whenShowInputIsTrue_thenInputIsVisible() {
        val state = CommerceState(
            isLoading = false,
            showNewCommerceInput = true,
            newCommerceName = "Tienda"
        )

        commerceRobot
            .setUpRobot(state)
            .assertTagIsDisplayed(CommerceTags.NewInput)
    }

    @Test
    fun whenTypingInInput_thenTriggerNameChangedEvent() {
        val state = CommerceState(
            isLoading = false,
            showNewCommerceInput = true,
            newCommerceName = ""
        )
        val inputText = "Nueva Tienda"

        commerceRobot
            .setUpRobot(state)
            .performTextInputToTag(CommerceTags.NewInput, inputText)
            .assertExpectedEvent(CommerceEvent.NewCommerceNameChanged(inputText))
    }

    @Test
    fun whenInputDoneAction_thenTriggerSendNewCommerceEvent() {
        val state = CommerceState(
            isLoading = false,
            showNewCommerceInput = true,
            newCommerceName = "Tienda Final"
        )

        commerceRobot
            .setUpRobot(state)
            .performTextInputImeActionToTag(CommerceTags.NewInput)
            .assertExpectedEvent(CommerceEvent.SendNewCommerce)
    }

    @Test
    fun whenSwipeToDeleteItem_thenTriggerRemoveCommerceEvent() {
        val commerceId = "99"
        val commerceName = "Tienda Borrar"
        val state = CommerceState(
            isLoading = false,
            commerceList = listOf(Commerce(id = commerceId, name = commerceName))
        )

        commerceRobot
            .setUpRobot(state)
            .performSwipeToDelete(commerceName)
            .assertExpectedEvent(CommerceEvent.RemoveCommerce(commerceId))
    }

    @Test
    fun whenClickEmptyState_thenTriggerAddNewCommerceEvent() {
        val state = CommerceState(
            isLoading = false,
            commerceList = emptyList()
        )

        commerceRobot
            .setUpRobot(state)
            .performClickOnTag(CommerceTags.EmptyList)
            .assertExpectedEvent(CommerceEvent.AddNewCommerce)
    }
}