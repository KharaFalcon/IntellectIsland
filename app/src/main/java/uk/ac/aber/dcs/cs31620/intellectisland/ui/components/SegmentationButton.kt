package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavHostController
import androidx.compose.material3.Text
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen

@Composable
fun SegmentationButton(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val options = listOf("Add", "Edit")
    val icons = listOf(Icons.Default.Add, Icons.Default.Edit)
    val destinations = listOf(
        Screen.AddQuestions.route,
        Screen.RemoveQuestions.route
    ) // Navigation routes for each button

    MultiChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                modifier = Modifier.weight(1f), // Each button takes equal width
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = false, // No selection state required
                onCheckedChange = {
                    navController.navigate(route = destinations[index]) // Navigate to the corresponding screen
                },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = label
                    )
                },
                label = {
                    Text(text = label) // Label for each button
                }
            )
        }
    }
}
