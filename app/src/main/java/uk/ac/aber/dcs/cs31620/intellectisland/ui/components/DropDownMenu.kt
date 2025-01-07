package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun DropDownMenu(navController: NavHostController, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(color = primaryContainerLight, shape = RoundedCornerShape(8.dp))
        ) {
            // Home Menu Item
            DropdownMenuItem(
                text = { Text("Home", color = Color.White) }, // White text
                onClick = {
                    expanded = false // Close the menu
                    navController.navigate(Screen.HomeScreen.route)
                },
                modifier = Modifier
                    .background(color = primaryContainerLight, shape = RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit Icon",
                        tint = Color.White
                    ) // White icon
                }
            )

            // Settings Menu Item
            DropdownMenuItem(
                text = { Text("Settings", color = Color.White) }, // White text
                onClick = {
                    expanded = false // Close the menu
                    navController.navigate(Screen.UserProfile.route)
                },
                modifier = Modifier
                    .background(color = primaryContainerLight, shape = RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Settings Icon",
                        tint = Color.White
                    ) // White icon
                }
            )
        }
    }
}
