package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
@Composable
fun DropDownMenu(navController: NavHostController, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            // Home Menu Item
            DropdownMenuItem(
                text = { Text("Home") },
                onClick = {
                    expanded = false // Close the menu
                    // Navigate to Home Screen (update this with your specific navigation logic)
                    navController.navigate(Screen.HomeScreen.route)
                },
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = "Edit Icon") }
            )

            // Log Out Menu Item
            DropdownMenuItem(
                text = { Text("Log Out") },
                onClick = {
                    expanded = false // Close the menu
                    // Navigate to Log Out screen or perform log out
                  //  navController.navigate(Screen.LogOut.route)
                },
                leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings Icon") }
            )

            // Exit Quiz Menu Item
            DropdownMenuItem(
                text = { Text("Exit Quiz") },
                onClick = {
                    expanded = false // Close the menu
                    // Logic to exit the quiz (you may need to use a ViewModel or navigate)
                },
                leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings Icon") }
            )

            // Divider
            HorizontalDivider()

            // Send Feedback Menu Item
            DropdownMenuItem(
                text = { Text("Send Feedback") },
                onClick = {
                    expanded = false // Close the menu
                    // Logic to send feedback (could open a feedback form)
                },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "Email Icon") },
                trailingIcon = { Text("F11", textAlign = TextAlign.Center) }
            )
        }
    }
}
