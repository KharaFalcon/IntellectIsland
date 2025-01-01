package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun HomeScreen(navController: NavHostController) { // Ensure navController is passed here
    MainTopNavigationBar(navController) // Pass navController to MainTopNavigationBar
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Let's Begin", // Corrected text grammar
            fontSize = 40.sp,
            color = Color.Black,
            modifier = Modifier.padding(40.dp)
        )
        // Start Quiz Button
        OutlinedButton(
            onClick = {
                navController.navigate(route = Screen.StartQuiz.route)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
        ) {
            Text(
                text = "Quiz Mode",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp, start = 16.dp, end = 16.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp)) // Spacing between buttons

        // Management Mode Button
        OutlinedButton(
            onClick = {
                navController.navigate(route = Screen.AddQuestions.route)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
        ) {
            Text(
                text = "Management Mode",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp, start = 16.dp, end = 16.dp),
            )
        }
    }
}
