package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.dcs.cs31620.intellectisland.model.QuestionData
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inverseOnSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onPrimaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSecondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.outlineVariantLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.surfaceLight

@Composable
fun QuestionsList(
    questions: List<QuestionData>,
    onEditButtonClick: (QuestionData) -> Unit,
    icon: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        questions.forEachIndexed { index, question ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = surfaceLight,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(1.dp, outlineVariantLight, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = secondaryContainerLight,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = onSecondaryContainerLight
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = question.questionText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                    }

                    IconButton(
                        onClick = { onEditButtonClick(question) }
                    ) {
                        Icon(
                            imageVector = if (icon == "edit") Icons.Default.Edit else Icons.Default.Delete,
                            contentDescription = if (icon == "edit") "Edit Question" else "Delete Question",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
