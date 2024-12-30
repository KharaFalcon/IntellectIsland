package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun AddQuestions(navController: NavHostController) {
    MainTopNavigationBar()

    // State to hold the question bank
    val questionBank = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add a question",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp, top = 130.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(100.dp))

        var textFieldValue by remember { mutableStateOf("") }

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Enter your question here") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 50.dp),
            singleLine = true
        )

        OutlinedButton(
            onClick = {
                if (textFieldValue.isNotBlank()) {
                    questionBank.add(textFieldValue)
                    textFieldValue = "" // Clear the text field
                }
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Add Question",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp, start = 16.dp, end = 16.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Questions in the bank:",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Display the list of questions
        Column(modifier = Modifier.padding(top = 8.dp)) {
            questionBank.forEach { question ->
                Text(
                    text = question,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
