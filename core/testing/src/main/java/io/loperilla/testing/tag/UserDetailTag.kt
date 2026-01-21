package io.loperilla.testing.tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.tag
 * Created By Manuel Lopera on 26/12/25 at 19:59
 * All rights reserved 2025
 */

sealed class UserDetailTag(override val name: String) : Tag {
    data object UserDetailRootTag : UserDetailTag(USER_DETAIL_ROOT_TAG)
    data object UserDetailTopBar : UserDetailTag(USER_DETAIL_TOPBAR)
    data object UserDetailImage : UserDetailTag(USER_DETAIL_IMAGE)
    data object UserDetailNameInput : UserDetailTag(USER_DETAIL_NAME_INPUT)
    data object UserDetailSaveButton : UserDetailTag(USER_DETAIL_SAVE_BUTTON)
    data object UserDetailLoading : UserDetailTag(USER_DETAIL_LOADING)
}

private const val USER_DETAIL_ROOT_TAG = "user_detail_root"
private const val USER_DETAIL_TOPBAR = "user_detail_topbar"
private const val USER_DETAIL_IMAGE = "user_detail_image"
private const val USER_DETAIL_NAME_INPUT = "user_detail_name_input"
private const val USER_DETAIL_SAVE_BUTTON = "user_detail_save_button"
private const val USER_DETAIL_LOADING = "user_detail_loading"
