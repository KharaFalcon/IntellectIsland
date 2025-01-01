package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun QuestionProgressBar(currentQuestionIndex: Int, totalQuestions: Int) {
    // Calculate progress as a percentage (e.g., 0.0 to 1.0)
    val progress = if (totalQuestions > 0) {
        (currentQuestionIndex + 1).toFloat() / totalQuestions
    } else {
        0f
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
            style = MaterialTheme.typography.bodyMedium
        )

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}
