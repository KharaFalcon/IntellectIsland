package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.R
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.viewmodel.QuestionViewModel

@Composable
fun UserName(navController: NavHostController, viewModel: QuestionViewModel) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome",
            color = Color.Black,
            fontSize = 50.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )

        Image(
            modifier = Modifier
                .size(500.dp)
                .padding(10.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.nav_image)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedButton(
            onClick = {
                // Save the username and navigate
                viewModel.updateQuestionUserName(0, name) // Example call; update ID logic as needed
                navController.navigate(route = Screen.HomeScreen.route)
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
        ) {
            Text(
                text = "Next",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 150.dp, top = 20.dp, bottom = 20.dp),
                color = Color.White,
                fontSize = 30.sp,
            )
        }
    }
}
