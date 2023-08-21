package io.loperilla.model.database

/*****
 * Project: CompraCasa
 * From: io.loperilla.model.database
 * Created By Manuel Lopera on 25/4/23 at 18:58
 * All rights reserved 2023
 */
sealed class DatabaseResult<T> {
    data class GET<T>(val getResponse: List<T>) : DatabaseResult<T>()

    data class GET_SINGLE<T>(val item: T) : DatabaseResult<T>()

    data class FAIL<T>(val errorMessage: String = "") : DatabaseResult<T>()

}
