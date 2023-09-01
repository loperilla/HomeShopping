package io.loperilla.model.database.result

/*****
 * Project: HomeShopping
 * From: io.loperilla.model.database.result
 * Created By Manuel Lopera on 31/8/23 at 16:56
 * All rights reserved 2023
 */
sealed class PostDatabaseResult {
    data object SUCCESS : PostDatabaseResult()
    data class FAIL(val exception: Exception) : PostDatabaseResult()
}
