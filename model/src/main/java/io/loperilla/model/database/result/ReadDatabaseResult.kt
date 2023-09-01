package io.loperilla.model.database.result

/*****
 * Project: CompraCasa
 * From: io.loperilla.model.database
 * Created By Manuel Lopera on 25/4/23 at 18:58
 * All rights reserved 2023
 */
sealed class ReadDatabaseResult<T> {
    data class SUCCESS<T>(val result: T) : ReadDatabaseResult<T>()
    data class FAIL<T>(val errorMessage: String = "") : ReadDatabaseResult<T>()
}
