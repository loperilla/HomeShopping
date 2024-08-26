package io.loperilla.onboarding_domain.repository

import io.loperilla.onboarding_domain.model.database.Commerce
import kotlinx.coroutines.flow.Flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository.database
 * Created By Manuel Lopera on 11/8/24 at 12:05
 * All rights reserved 2024
 */
interface CommerceRepository {
    suspend fun getCommerces(): Flow<List<Commerce>>
}
