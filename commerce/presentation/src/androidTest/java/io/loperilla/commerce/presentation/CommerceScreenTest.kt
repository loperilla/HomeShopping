package io.loperilla.commerce.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import io.loperilla.domain.model.commerce.Commerce
import io.loperilla.presentation.CommerceEvent
import io.loperilla.presentation.CommerceScreen
import io.loperilla.presentation.CommerceState
import io.loperilla.testing.tag.COMMERCE_EMPTY_LIST
import io.loperilla.testing.tag.COMMERCE_FAB
import io.loperilla.testing.tag.COMMERCE_LIST
import io.loperilla.testing.tag.COMMERCE_LOADING
import io.loperilla.testing.tag.COMMERCE_TOPBAR
import io.loperilla.testing.tag.NEW_COMMERCE_INPUT
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert
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

    @Test
    fun whenLoadingState_thenShowLoadingAndHideControls() {
        // GIVEN
        val state = CommerceState(isLoading = true)

        // WHEN
        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = {})
        }

        // THEN
        // El loading debe verse
        composeTestRule.onNodeWithTag(COMMERCE_LOADING).assertIsDisplayed()

        // TopBar y FAB están dentro de un if (!state.isLoading), por lo que no deben existir en el árbol
        composeTestRule.onNodeWithTag(COMMERCE_TOPBAR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(COMMERCE_FAB).assertDoesNotExist()
    }

    @Test
    fun whenDataLoaded_thenShowCommerceList() {
        // GIVEN
        val commerces = listOf(
            Commerce(id = "1", name = "Supermercado"),
            Commerce(id = "2", name = "Ferretería")
        )
        val state = CommerceState(
            isLoading = false,
            commerceList = commerces
        )

        // WHEN
        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = {})
        }

        // THEN
        composeTestRule.onNodeWithTag(COMMERCE_TOPBAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(COMMERCE_FAB).assertIsDisplayed()

        // La lista debe estar visible
        composeTestRule.onNodeWithTag(COMMERCE_LIST).assertIsDisplayed()
        // Validamos que se renderizan los items por su texto
        composeTestRule.onNodeWithText("Supermercado").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ferretería").assertIsDisplayed()

        // El empty state no debe verse
        composeTestRule.onNodeWithTag(COMMERCE_EMPTY_LIST).assertDoesNotExist()
    }

    @Test
    fun whenListIsEmpty_thenShowEmptyState() {
        // GIVEN
        val state = CommerceState(
            isLoading = false,
            commerceList = emptyList()
        )

        // WHEN
        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = {})
        }

        // THEN
        composeTestRule.onNodeWithTag(COMMERCE_EMPTY_LIST).assertIsDisplayed()
        composeTestRule.onNodeWithTag(COMMERCE_LIST).assertDoesNotExist()
    }

    @Test
    fun whenClickOnBack_thenTriggerGoBackEvent() {
        // GIVEN
        var capturedEvent: CommerceEvent? = null
        val state = CommerceState(isLoading = false)

        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = { capturedEvent = it })
        }

        // WHEN
        // Tu TopBar tiene el tag COMMERCE_TOPBAR, pero el botón de atrás es un hijo.
        // Buscamos un nodo que tenga acción de click Y cuyo ancestro sea la TopBar.
        // (Ojo: Esto asume que el botón de atrás es el único clickable dentro de la TopBar en este estado)
        composeTestRule.onNode(
            hasClickAction() and hasAnyAncestor(hasTestTag(COMMERCE_TOPBAR))
        ).performClick()

        // THEN
        Assert.assertEquals(CommerceEvent.GoBack, capturedEvent)
    }

    @Test
    fun whenClickAddCommerceFAB_thenTriggerAddNewCommerceEvent() {
        // GIVEN
        var capturedEvent: CommerceEvent? = null
        val state = CommerceState(isLoading = false)

        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = { capturedEvent = it })
        }

        // WHEN
        composeTestRule.onNodeWithTag(COMMERCE_FAB).performClick()

        // THEN
        Assert.assertEquals(CommerceEvent.AddNewCommerce, capturedEvent)
    }

    @Test
    fun whenShowInputIsTrue_thenInputIsVisible() {
        // GIVEN
        val state = CommerceState(
            isLoading = false,
            showNewCommerceInput = true,
            newCommerceName = "Tienda"
        )

        // WHEN
        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = {})
        }

        // THEN
        composeTestRule.onNodeWithTag(NEW_COMMERCE_INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithText("Tienda").assertIsDisplayed()
    }

    @Test
    fun whenTypingInInput_thenTriggerNameChangedEvent() {
        // GIVEN
        var capturedEvent: CommerceEvent? = null
        val state = CommerceState(
            isLoading = false,
            showNewCommerceInput = true,
            newCommerceName = ""
        )

        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = { capturedEvent = it })
        }

        // WHEN
        val inputText = "Nueva Tienda"
        composeTestRule.onNodeWithTag(NEW_COMMERCE_INPUT).performTextInput(inputText)

        // THEN
        // Verificamos el último evento capturado
        assert(capturedEvent is CommerceEvent.NewCommerceNameChanged)
        // Nota: performTextInput puede enviar el texto completo o parciales dependiendo de la implementación
        // Aquí validamos que el evento sea del tipo correcto.
    }

    @Test
    fun whenInputDoneAction_thenTriggerSendNewCommerceEvent() {
        // GIVEN
        var capturedEvent: CommerceEvent? = null
        val state = CommerceState(
            isLoading = false,
            showNewCommerceInput = true,
            newCommerceName = "Tienda Final"
        )

        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = { capturedEvent = it })
        }

        // WHEN
        composeTestRule.onNodeWithTag(NEW_COMMERCE_INPUT).performImeAction()

        // THEN
        assertEquals(CommerceEvent.SendNewCommerce, capturedEvent)
    }

    @Test
    fun whenSwipeToDeleteItem_thenTriggerRemoveCommerceEvent() = runTest{
        // GIVEN
        var capturedEvent: CommerceEvent? = null
        val commerceId = "99"
        val state = CommerceState(
            isLoading = false,
            commerceList = listOf(Commerce(id = commerceId, name = "Tienda Borrar"))
        )

        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = { capturedEvent = it })
        }

        // WHEN
        // Tu SwipeBox usa 'endToStartSwipe' (derecha a izquierda)
        composeTestRule.onNodeWithText("Tienda Borrar").performTouchInput {
            swipeLeft()
        }
        composeTestRule.awaitIdle()
        // THEN
        assertEquals(CommerceEvent.RemoveCommerce(commerceId), capturedEvent)
    }

    @Test
    fun whenClickEmptyState_thenTriggerAddNewCommerceEvent() {
        // GIVEN
        var capturedEvent: CommerceEvent? = null
        val state = CommerceState(
            isLoading = false,
            commerceList = emptyList()
        )

        composeTestRule.setContent {
            CommerceScreen(state = state, onEvent = { capturedEvent = it })
        }

        // WHEN
        // Tu HomeShoppingCard dentro de EmptyCommerceListScreen tiene el tag y es clickable
        composeTestRule.onNodeWithTag(COMMERCE_EMPTY_LIST).performClick()

        // THEN
        Assert.assertEquals(CommerceEvent.AddNewCommerce, capturedEvent)
    }
}