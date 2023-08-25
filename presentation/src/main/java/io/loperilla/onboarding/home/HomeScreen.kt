package io.loperilla.onboarding.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.loperilla.core_ui.HomeShoppingTheme
import io.loperilla.core_ui.MEDIUM
import io.loperilla.core_ui.previews.PIXEL_33_NIGHT
import io.loperilla.core_ui.routes.Routes

/*****
 * Project: CompraCasa
 * From: io.loperilla.onboarding.home
 * Created By Manuel Lopera on 20/8/23 at 18:58
 * All rights reserved 2023
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(newDestination: (String) -> Unit) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val logoutFinish by homeViewModel.logoutFinish.collectAsStateWithLifecycle()
    val showLogoutDialog by homeViewModel.showLogoutDialog.collectAsStateWithLifecycle()
    val shoppingBuyList by homeViewModel.shoppingBuyList.collectAsStateWithLifecycle()

    if (logoutFinish) {
        newDestination(Routes.AUTH.route)
        return
    }
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Home Shopping")
                },
                actions = {
                    IconButton(onClick = { homeViewModel.showLogoutDialog() }) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = "Salir de la aplicación"
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (showLogoutDialog) {
                AlertDialog(
                    icon = {
                        Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = "ExitApp")
                    },
                    onDismissRequest = { homeViewModel.hideLogoutDialog() },
                    title = { Text("¿Estás seguro que quieres cerrar sesión") },
                    confirmButton = {
                        TextButton(onClick = { homeViewModel.doLogout() }) {
                            Text("Delete it".uppercase())
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { homeViewModel.hideLogoutDialog() }) {
                            Text("Cancel".uppercase())
                        }
                    }
                )
            }
            if (shoppingBuyList.isNotEmpty()) {
                LazyColumn {

                }
            } else {
                EmptyShoppingBuyList()
            }
        }
    }
}


@Composable
fun EmptyShoppingBuyList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MEDIUM),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "Carrito de la compra vacío",
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(MEDIUM))
        Text(
            text = "No hay carritos registrados",
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center
        )
    }
}

@PIXEL_33_NIGHT
@Composable
fun EmptyShoppingBuyListPrev() {
    HomeShoppingTheme {
        Surface {
            EmptyShoppingBuyList()
        }
    }
}
