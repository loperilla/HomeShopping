package io.loperilla.onboarding_domain.usecase.query

import io.loperilla.onboarding_domain.repository.QueryRepository

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.shoppingcart
 * Created By Manuel Lopera on 26/8/23 at 19:58
 * All rights reserved 2023
 */
class QueryModel(
    private val queryRepository: QueryRepository
) {
    fun getAllQueries() = queryRepository.getQueries()

    fun insertNewQuery(query: String) = queryRepository.insertQuery(query)
    fun removeQuery(queryToDelete: String) = queryRepository.removeQuery(queryToDelete)
    suspend fun deleteAll() = queryRepository.deleteAll()
}