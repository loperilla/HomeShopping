package io.loperilla.onboarding.home

import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 10/8/24 at 11:17
 * All rights reserved 2024
 */
data class HomeState(
    val showAreYouSureLogout: Boolean = false,
    val commerceList: List<Commerce> = emptyList(),
    val commerceListSelected: List<Long> = emptyList(),
    val commerceListIsVisible: Boolean = false,
)
