package io.loperilla.datasource.firebase.reference

import com.google.firebase.database.DatabaseReference

/*****
 * Project: CompraCasa
 * From: io.loperilla.datasource.firebase.reference
 * Created By Manuel Lopera on 25/4/23 at 20:18
 * All rights reserved 2023
 */
sealed class CustomReference {
    data class SHOPPING_LIST_REFERENCE(
        val shoppingReference: DatabaseReference
    ) : CustomReference()
}
