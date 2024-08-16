package io.loperilla.data.repository.database

import io.loperilla.data.model.CommerceModel
import kotlinx.coroutines.flow.Flow

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.repository.database
 * Created By Manuel Lopera on 11/8/24 at 12:05
 * All rights reserved 2024
 */
interface CommerceRepository {
    suspend fun getCommerces(): Flow<List<CommerceModel>>
}