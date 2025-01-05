package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
@Composable
fun HomeScreen(navController: NavHostController) {
    // Initialize coroutineScope and snackbarHostState
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // MainTopNavigationBar
    MainTopNavigationBar(navController) // Pass navController to MainTopNavigationBar

    // Scaffold for the entire page structure
    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState, // Pass the snackbarHostState here
        pageContent = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                // Content within the column
                Text(
                    text = "Let's Begin", // Corrected text grammar
                    fontSize = 40.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 60.dp, end = 180.dp)
                )
                Spacer(modifier = Modifier.height(80.dp))
                // Start Quiz Button
                OutlinedButton(
                    onClick = {
                        navController.navigate(route = Screen.StartQuiz.route)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
                ) {
                    Text(
                        text = "Quiz Mode",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 60.dp, top = 60.dp, start = 80.dp, end = 80.dp),
                    )
                }

                Spacer(modifier = Modifier.height(80.dp)) // Spacing between buttons

                // Management Mode Button
                OutlinedButton(
                    onClick = {
                        navController.navigate(route = Screen.AddQuestions.route)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
                ) {
                    Text(
                        text = "Management Mode",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 60.dp, top = 60.dp, start = 30.dp, end = 30.dp),
                    )
                }
            }
        }
    )
}
