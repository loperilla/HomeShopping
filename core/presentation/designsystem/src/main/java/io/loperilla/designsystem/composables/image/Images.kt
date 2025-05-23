package io.loperilla.designsystem.composables.image

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import io.loperilla.designsystem.composables.loading.ImageLoadingAnimation

/*****
 * Project: HomeShopping
 * From: io.loperilla.designsystem.composables.image
 * Created By Manuel Lopera on 9/3/25 at 17:51
 * All rights reserved 2025
 */

@Composable
fun UrlImage(
    imageUrl: String,
    imageName: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .memoryCacheKey("${imageName}_image")
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .scale(Scale.FILL)
            .build(),
        loading = {
            ImageLoadingAnimation()
        },
        error = {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "${imageName}_image",
                modifier = modifier
            )
        },
        contentDescription = "${imageName}_image",
        modifier = modifier
    )
}

@Composable
fun UriImage(
    uriImage: Uri?,
    imageName: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(uriImage)
            .crossfade(true)
            .memoryCacheKey("${imageName}_image")
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .scale(Scale.FILL)
            .build(),
        loading = {
            ImageLoadingAnimation()
        },
        error = {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "${imageName}_image",
                modifier = modifier
            )
        },
        contentDescription = "${imageName}_image",
        modifier = modifier
    )
}
