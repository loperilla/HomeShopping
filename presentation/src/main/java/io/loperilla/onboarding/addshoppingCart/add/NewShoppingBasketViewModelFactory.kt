package io.loperilla.onboarding.addshoppingCart.add

import dagger.assisted.AssistedFactory

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.new
 * Created By Manuel Lopera on 25/8/24 at 16:20
 * All rights reserved 2024
 */
@AssistedFactory
interface NewShoppingBasketViewModelFactory {
    fun create(commerceId: String): NewShoppingBasketViewModel
}