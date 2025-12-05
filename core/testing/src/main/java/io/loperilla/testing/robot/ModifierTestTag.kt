package io.loperilla.testing.robot

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import io.loperilla.testing.tag.Tag

/*****
 * Project: HomeShopping
 * From: io.loperilla.testing.robot
 * Created By Manuel Lopera on 2/12/25 at 19:42
 * All rights reserved 2025
 */

fun Modifier.testTag(tag: Tag): Modifier = this.testTag(tag.name)