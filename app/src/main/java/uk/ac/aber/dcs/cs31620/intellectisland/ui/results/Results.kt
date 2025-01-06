package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizMode

import QuestionViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
@Composable
fun Results(
    navController: NavHostController,
    viewModel: QuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val score by viewModel.calculateScore().observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, top= 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Quiz Results", fontSize = 28.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your Score: $score", fontSize = 24.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
            Text(text = "Go to Home")
        }
    }
}
