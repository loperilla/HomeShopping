package io.loperilla.onboarding_domain.usecase.query

import io.loperilla.data.repository.QueryRepository
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.shoppingcart
 * Created By Manuel Lopera on 26/8/23 at 19:58
 * All rights reserved 2023
 */
class QueryModel @Inject constructor(
    private val queryRepository: QueryRepository
) {
    fun getAllQueries() = queryRepository.getQueries()

    fun insertNewQuery(query: String) = queryRepository.insertNewQuery(query)
    fun removeQuery(queryToDelete: String) = queryRepository.removeQuery(queryToDelete)
}