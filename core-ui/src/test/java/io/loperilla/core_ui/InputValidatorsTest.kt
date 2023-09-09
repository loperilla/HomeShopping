package io.loperilla.core_ui

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.loperilla.core_ui.input.InputValidators
import org.junit.jupiter.api.Test

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.input
 * Created By Manuel Lopera on 9/9/23 at 11:22
 * All rights reserved 2023
 */
class InputValidatorsTest {
    @Test
    fun `Write Email Valid, return true`() {
        // GIVEN
        val emailToTest = "hola@gmail.com"

        // ACTION
        val result = InputValidators.EMAIL.isValid(emailToTest)

        // ASSERTION
        assertThat(result).isTrue()
    }

    @Test
    fun `Write wrong email, return false`() {
        // GIVEN
        val emailToTest = "holagmail.com"

        // ACTION
        val result = InputValidators.EMAIL.isValid(emailToTest)

        // ASSERTION
        assertThat(result).isFalse()
    }

    @Test
    fun `Write Password Valid, return true`() {
        // GIVEN
        val password = "prueba"

        // ACTION
        val result = InputValidators.PASSWORD.isValid(password)

        // ASSERTION
        assertThat(result).isTrue()
    }

    @Test
    fun `Write wrong Password, return false`() {
        // GIVEN
        val password = "hola"

        // ACTION
        val result = InputValidators.PASSWORD.isValid(password)

        // ASSERTION
        assertThat(result).isFalse()
    }
}
