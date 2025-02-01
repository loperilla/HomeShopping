package io.loperilla.onboarding.navigator

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import io.loperilla.onboarding_domain.model.database.Commerce
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.navigator
 * Created By Manuel Lopera on 22/9/24 at 13:08
 * All rights reserved 2024
 */

val CommerceType = object : NavType<Commerce> (
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): Commerce? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): Commerce {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: Commerce): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: Commerce) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
