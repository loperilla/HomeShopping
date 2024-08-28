package io.loperilla.core_ui.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import io.loperilla.core_ui.Screen
import io.loperilla.core_ui.TextBodySize
import io.loperilla.core_ui.TextSmallSize
import io.loperilla.core_ui.TextTitleSize
import io.loperilla.core_ui.myFontFamily
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.spacers.LowSpacer

/*****
 * Project: HomeShopping
 * From: io.loperilla.core_ui.text
 * Created By Manuel Lopera on 8/9/23 at 18:16
 * All rights reserved 2023
 */

@Composable
fun TextRegular(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = TextBodySize,
    textColor: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text,
        modifier,
        color = textColor,
        fontSize = textSize,
        fontWeight = FontWeight.Medium,
        fontFamily = myFontFamily,
        textAlign = textAlign
    )
}

@Composable
fun TextSemiBold(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = TextBodySize,
    textColor: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text,
        modifier,
        color = textColor,
        fontSize = textSize,
        fontWeight = FontWeight.SemiBold,
        fontFamily = myFontFamily,
        textAlign = textAlign
    )
}

@Composable
fun TextTitle(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = TextTitleSize,
    textColor: Color = Color.Unspecified
) {
    Text(
        text,
        modifier,
        color = textColor,
        fontSize = textSize,
        fontWeight = FontWeight.Bold,
        fontFamily = myFontFamily
    )
}

@Composable
fun TextThin(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = TextSmallSize,
    textColor: Color = Color.Unspecified
) {
    Text(
        text,
        modifier,
        color = textColor,
        fontSize = textSize,
        fontWeight = FontWeight.Thin,
        fontFamily = myFontFamily
    )
}

@PIXEL_33_NIGHT
@Composable
fun TextsPreview() {
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TextRegular("Soy TextRegular")
            LowSpacer()
            TextTitle("Soy TextTitle")
            LowSpacer()
            TextThin("Soy TextThin")
            LowSpacer()
            TextSemiBold("Soy TextSemiBold")
        }
    }
}
