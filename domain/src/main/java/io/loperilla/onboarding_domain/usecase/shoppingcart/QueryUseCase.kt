package io.loperilla.onboarding_domain.usecase.shoppingcart

import io.loperilla.data.repository.QueryRepository
import javax.inject.Inject

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding_domain.usecase.shoppingcart
 * Created By Manuel Lopera on 26/8/23 at 19:58
 * All rights reserved 2023
 */
class QueryUseCase @Inject constructor(
    private val queryRepository: QueryRepository
) {
    operator fun invoke(query: String) = queryRepository.getQueriesBy(query)

    fun insertNewQuery(query: String) = queryRepository.insertNewQuery(query)
    suspend fun removeQuery(queryToDelete: String) = queryRepository.removeQuery(queryToDelete)
}