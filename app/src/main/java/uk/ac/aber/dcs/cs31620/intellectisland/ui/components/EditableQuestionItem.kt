package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.datasource.model.QuestionData

@Composable
fun EditableQuestionItem(
    navController: NavHostController,
    question: QuestionData,
    onQuestionChange: (QuestionData) -> Unit
) {
    var questionText by remember { mutableStateOf(question.questionText) }
    val options = remember { mutableStateListOf(*question.options.toTypedArray()) }
    var correctAnswerIndex by remember { mutableStateOf(question.correctAnswerIndex) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var optionToDeleteIndex by remember { mutableStateOf(-1) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Segmentation Button
        SegmentationButton(
            modifier = Modifier.align(Alignment.Start),
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
        Spacer(modifier = Modifier.height(16.dp))

        // Editable Options Header
        Text(text = "Options", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Editable Options List
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Editable Option TextField
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

                // Remove Option Button
                IconButton(
                    onClick = {
                        showDeleteDialog = true
                        optionToDeleteIndex = index
                    },
                    enabled = options.size > 1 // Disable button if only one option remains
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove Option"
                    )
                }

                // RadioButton for Correct Answer Selection
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Option Button
        Button(
            onClick = {
                // Validate inputs
                if (options.any { it.isBlank() }) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Cannot add: Empty options exist.")
                    }
                } else if (options.size < 10) {
                    options.add("") // Add a blank option
                    onQuestionChange(
                        question.copy(
                            options = options,
                            correctAnswerIndex = correctAnswerIndex
                        )
                    )
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Cannot add: Maximum of 10 options allowed.")
                    }
                }
            },
            enabled = options.size < 10, // Disable button if options are at the limit
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add Option")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Snackbar Host
        SnackbarHost(hostState = snackbarHostState)
    }

    // Confirmation Dialog
    if (showDeleteDialog && optionToDeleteIndex >= 0) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Remove the option
                        options.removeAt(optionToDeleteIndex)
                        if (correctAnswerIndex >= options.size) {
                            correctAnswerIndex = options.lastIndex
                        }
                        onQuestionChange(
                            question.copy(
                                options = options,
                                correctAnswerIndex = correctAnswerIndex
                            )
                        )
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this option? This action cannot be undone.") }
        )
    }
}
