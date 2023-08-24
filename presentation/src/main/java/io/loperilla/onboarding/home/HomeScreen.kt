package io.loperilla.onboarding.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        }
    }
}
