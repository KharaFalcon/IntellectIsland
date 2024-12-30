package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun AddAnswers(navController: NavHostController) {
    MainTopNavigationBar()

    // State to hold the question bank and the selected radio button
    val questionBank = remember { mutableStateListOf<String>() }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswerAdded by remember { mutableStateOf(false) }

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
        Spacer(modifier = Modifier.height(50.dp))

        // Box with a purple square around the question
        Box(
            modifier = Modifier
                .background(Color(0xFF9C27B0), RoundedCornerShape(16.dp)) // Purple color with rounded corners
                .padding(16.dp)
        ) {
            Text(
                text = "Question goes here",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        var textFieldValue by remember { mutableStateOf("") }

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Enter your answer here") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 50.dp),
            singleLine = true
        )

        OutlinedButton(
            onClick = {
                if (textFieldValue.isNotBlank()) {
                    questionBank.add(textFieldValue)
                    isAnswerAdded = true  // Set to true when the first answer is added
                    textFieldValue = "" // Clear the text field
                }
            },
            colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Add Answer",
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

        Spacer(modifier = Modifier.height(16.dp))

        // Display the answers as radio buttons
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.Start
        ) {
            questionBank.forEach { answer ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = answer == selectedAnswer,
                        onClick = { selectedAnswer = answer },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = answer, fontSize = 18.sp)
                }
            }
        }

        // Show the "Submit Question" button once at least one answer is added
        if (isAnswerAdded) {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                colors = ButtonDefaults.outlinedButtonColors(containerColor = primaryContainerLight),
                onClick = {
                    navController.navigate(route = Screen.EditQuestions.route)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Submit Question",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}
