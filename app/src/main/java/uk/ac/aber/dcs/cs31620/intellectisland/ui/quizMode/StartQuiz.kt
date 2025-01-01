package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.CircularProgressIndicator
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.tertiaryContainerLight

@Composable
fun StartQuiz(navController: NavHostController) {
    MainTopNavigationBar(navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quiz Name",
            fontSize = 40.sp,
            color = Color.Gray,
            modifier = Modifier.padding(40.dp)
        )
        CircularProgressIndicator(
            progress = 1f, // 50% progress
            question = "Questions",
            largeNumber = 5,
            circleColor = tertiaryContainerLight ,
            trackColor = secondaryContainerLight,
            circleSize = 200f,
            strokeWidth = 12f)

        Spacer(modifier = Modifier.height(60.dp))

        // Start Quiz Button
        OutlinedButton(
            onClick = {
                navController.navigate(Screen.Question.route)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
        ) {
            Text(
                text = "Start Quiz",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(
                    bottom = 16.dp,
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            )
        }

    }
}