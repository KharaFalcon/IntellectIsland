package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopLevelScaffold(
    navController: NavHostController,
    floatingActionButton: @Composable () -> Unit = { },
    snackbarContent: @Composable (SnackbarData) -> Unit = {},
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState? = null,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    // Remember the state of the drawer for toggling
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    Scaffold(
        topBar = {
            // MainTopNavigationBar is added here as the top app bar
            MainTopNavigationBar(
                navController = navController,
                onClick = {
                    // Toggle the drawer state on click
                    coroutineScope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                }
            )
        },
        floatingActionButton = floatingActionButton,
        snackbarHost = {
            snackbarHostState?.let {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    snackbarContent(data)
                }
            }
        },
        bottomBar = {
            // Bottom navigation bar is placed at the bottom
            MainBottomNavigationBar(navController)
        },
        content = { innerPadding ->
            // The page content with padding to avoid overlap with the bottom navigation
            pageContent(innerPadding)
        }
    )
}
