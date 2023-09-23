package io.loperilla.onboarding.additem

import android.graphics.Bitmap

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 16/9/23 at 20:07
 * All rights reserved 2023
 */
sealed class AddItemEvent {
    data class ProductNameChanged(val productName: String) : AddItemEvent()
    data object EnablePager : AddItemEvent()
    data object DisablePager : AddItemEvent()
    data class BitmapWasSelected(val newBitmap: Bitmap) : AddItemEvent()
    data class CommerceClicked(val commerceName: String) : AddItemEvent()
    data object AddProductButtonClicked : AddItemEvent()
}
