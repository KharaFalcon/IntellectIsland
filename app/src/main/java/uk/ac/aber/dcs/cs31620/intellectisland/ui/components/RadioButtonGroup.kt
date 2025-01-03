package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.inverseOnSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onPrimaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onPrimaryLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSecondaryContainerLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.secondaryContainerLight
@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    val letters = listOf("A", "B", "C", "D") // Add more letters if needed

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = secondaryContainerLight,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = inverseOnSurfaceLight,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                        .clickable { onOptionSelected(option) }
                ) {
                    // Circle with letter
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = if (selectedOption == option) Color.Gray else onPrimaryLight,
                                shape = RoundedCornerShape(15.dp)
                            )
                    ) {
                        Text(
                            text = letters.getOrNull(index) ?: "", // Use the letter or an empty string
                            color = onSecondaryContainerLight ,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Option text
                    Text(
                        text = option,
                        fontSize = 16.sp,
                        color = Color.Black, // All text is black
                        modifier = Modifier.weight(1f) // Use weight to push RadioButton to the end
                    )

                    // Radio button on the right
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { onOptionSelected(option) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}
