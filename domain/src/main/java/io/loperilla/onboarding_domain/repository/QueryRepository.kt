package io.loperilla.onboarding_domain.repository

import kotlinx.coroutines.flow.Flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.repository
 * Created By Manuel Lopera on 25/8/24 at 19:28
 * All rights reserved 2024
 */
interface QueryRepository {
    fun getQueries(): Flow<List<String>>
    fun insertQuery(query: String)
    fun removeQuery(queryToDelete: String)
}