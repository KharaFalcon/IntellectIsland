package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
@Composable
fun EditableQuestionItem(
    navController: NavHostController,
    question: QuestionData,
    onQuestionChange: (QuestionData) -> Unit
) {
    var questionText by remember { mutableStateOf(question.questionText) }
    val options = remember { mutableStateListOf(*question.options.toTypedArray()) }
    var correctAnswerIndex by remember { mutableStateOf(question.correctAnswerIndex) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        // Segmentation Button
        SegmentationButton(
            modifier = Modifier,
            navController = navController
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Editable Question Text
        TextField(
            value = questionText,
            onValueChange = { newText ->
                questionText = newText
                onQuestionChange(
                    question.copy(
                        questionText = newText,
                        options = options,
                        correctAnswerIndex = correctAnswerIndex
                    )
                )
            },
            label = { Text("Question Text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Editable Options
        Text(text = "Options", style = MaterialTheme.typography.bodyMedium)
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = option,
                    onValueChange = { newOption ->
                        options[index] = newOption
                        onQuestionChange(
                            question.copy(
                                options = options,
                                correctAnswerIndex = correctAnswerIndex
                            )
                        )
                    },
                    label = { Text("Option ${index + 1}") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                // RadioButton to select the correct answer
                RadioButton(
                    selected = index == correctAnswerIndex,
                    onClick = {
                        correctAnswerIndex = index
                        onQuestionChange(
                            question.copy(
                                options = options,
                                correctAnswerIndex = correctAnswerIndex
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Add Option Button
        Button(
            onClick = {
                options.add("") // Add a blank option
                onQuestionChange(
                    question.copy(
                        options = options,
                        correctAnswerIndex = correctAnswerIndex
                    )
                )
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add Option")
        }
    }
}
