package uk.ac.aber.dcs.cs31620.intellectisland.ui.quizManagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inverseOnSurfaceLight

/**
 * EditAnswersScreen
 * Allows users to select an answer for a quiz question
 */
@Composable
fun EditAnswers(navController: NavHostController) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    MainTopNavigationBar(navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "Edit",
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 110.dp)
                .align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        //starting point for quiz questions before db
        Text(
            text = "Which planet is nicknamed the ‘Red Planet’ due to its rusty surface?",
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        RadioButtonGroup(
            options = listOf("Mars", "Venus", "Jupiter", "Mercury", "Saturn"),
            selectedOption = selectedAnswer,
            onOptionSelected = { selectedAnswer = it }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle Next Button Click */ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = selectedAnswer != null,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Finish Editing",
                modifier = Modifier
                    .padding(end = 80.dp, start = 80.dp)
            )
        }
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = inverseOnSurfaceLight, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}