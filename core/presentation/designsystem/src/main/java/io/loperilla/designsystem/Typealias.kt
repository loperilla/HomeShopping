package io.loperilla.designsystem

import androidx.core.util.PatternsCompat

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui
 * Created By Manuel Lopera on 6/8/24 at 19:25
 * All rights reserved 2024
 */

typealias Email = String
typealias Password = String

val Password.isValidPassword: Boolean
    get() = this.isNotEmpty() && this.length >= 6
val Email.isValidEmail: Boolean
    get() = this.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()