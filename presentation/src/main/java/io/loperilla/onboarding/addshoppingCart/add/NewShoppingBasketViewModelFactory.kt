package io.loperilla.onboarding.addshoppingCart.add

import dagger.assisted.AssistedFactory
import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.new
 * Created By Manuel Lopera on 25/8/24 at 16:20
 * All rights reserved 2024
 */
@AssistedFactory
interface NewShoppingBasketViewModelFactory {
    fun create(commerce: Commerce): NewShoppingBasketViewModel
}