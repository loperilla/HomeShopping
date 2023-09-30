package io.loperilla.onboarding_domain.usecase.commerce

import io.loperilla.data.firebase.database.CommerceRepository
import io.loperilla.model.database.Commerce
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.commerce
 * Created By Manuel Lopera on 24/9/23 at 20:54
 * All rights reserved 2023
 */
class CommerceUseCase @Inject constructor(
    private val commerceRepository: CommerceRepository
) {
    operator fun invoke() = commerceRepository.getAllShopping()
    suspend fun addCommerce(commerce: Commerce) = commerceRepository.postItem(commerce)
}
