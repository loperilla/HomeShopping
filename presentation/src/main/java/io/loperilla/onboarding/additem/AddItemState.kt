package io.loperilla.onboarding.additem

import android.graphics.Bitmap

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding.additem
 * Created By Manuel Lopera on 16/9/23 at 20:03
 * All rights reserved 2023
 */
data class AddItemState(
    var isPagerEnabled: Boolean = true,
    var productName: String = "",
    var bitmap: Bitmap? = null,
    var commerceSelected: String = "",
    var isDropdownExpanded: Boolean = false,
    var addCommerceClicked: Boolean = false,
    var addItemRequestState: AddItemRequestState = AddItemRequestState.NONE
)

enum class AddItemRequestState {
    NONE, FAIL, SUCCESS
}
