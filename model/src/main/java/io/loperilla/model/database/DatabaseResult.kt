package io.loperilla.model.database

/*****
 * Project: CompraCasa
 * From: io.loperilla.model.database
 * Created By Manuel Lopera on 25/4/23 at 18:58
 * All rights reserved 2023
 */
sealed class DatabaseResult<T> {
    data class SUCCESS<T>(val result: T) : DatabaseResult<T>()
    data class FAIL<T>(val errorMessage: String = "") : DatabaseResult<T>()
}
