package io.loperilla.data.network

import com.google.firebase.firestore.CollectionReference

/*****
 * Project: HomeShopping
 * From: io.loperilla.data.network
 * Created By Manuel Lopera on 9/2/25 at 17:45
 * All rights reserved 2025
 */

const val FIREBASE_AUTH_ERROR_EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"

typealias ShoppingListCollection = CollectionReference
const val SHOPPING_LIST_COLLECTION_NAME: String = "shopping_list"