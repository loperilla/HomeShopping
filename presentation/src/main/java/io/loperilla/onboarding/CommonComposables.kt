package io.loperilla.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.loperilla.core_ui.CommerceChip
import io.loperilla.core_ui.MEDIUM
import io.loperilla.onboarding_domain.model.database.Commerce

/*****
 * Project: HomeShopping
 * From: io.loperilla.onboarding
 * Created By Manuel Lopera on 28/8/24 at 11:53
 * All rights reserved 2024
 */

@Composable
fun FlowCommerce(
    commerceList: List<Commerce>,
    onCommerceClicked: (Commerce) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(commerceList.size) {
            CommerceChip(
                text = commerceList[it].name,
                isSelected = commerceList[it].isSelected,
                onClick = {
                    onCommerceClicked(commerceList[it])
                }
            )
        }
    }
}
