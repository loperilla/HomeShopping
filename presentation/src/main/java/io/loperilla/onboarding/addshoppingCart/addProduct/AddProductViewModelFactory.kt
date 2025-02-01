package io.loperilla.onboarding.addshoppingCart.addProduct

import dagger.assisted.AssistedFactory
import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.addshoppingCart.addProduct
 * Created By Manuel Lopera on 27/8/24 at 12:51
 * All rights reserved 2024
 */
@AssistedFactory
interface AddProductViewModelFactory {
    fun create(commerce: Commerce): AddProductViewModel
}