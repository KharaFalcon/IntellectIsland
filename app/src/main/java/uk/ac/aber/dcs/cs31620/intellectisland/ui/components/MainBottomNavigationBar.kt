package uk.ac.aber.dcs.cs31620.intellectisland.ui.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.intellectisland.R
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation.screens
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.IntellectIslandTheme
@Composable
fun MainBottomNavigationBar(
    navController: NavHostController
) {
    val icons = mapOf(
        Screen.HomeScreen to IconGroup(
            filledIcon = Icons.Filled.Home,
            outlineIcon = Icons.Outlined.Home,
            label = stringResource(id = R.string.home)
        ),
        Screen.StartQuiz to IconGroup(
            filledIcon = Icons.Filled.Quiz,
            outlineIcon = Icons.Outlined.Quiz,
            label = stringResource(id = R.string.quiz_mode)
        ),
        Screen.RemoveQuestions to IconGroup(
            filledIcon = Icons.Filled.EditNote,
            outlineIcon = Icons.Outlined.EditNote,
            label = stringResource(R.string.management_mode)
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        // Loop through each screen to create a navigation item
        listOf(Screen.HomeScreen, Screen.StartQuiz, Screen.EditQuestionScreen).forEach { screen ->
            val isSelected = currentDestination
                ?.hierarchy?.any { it.route == screen.route } == true

            // Use safe access to get the corresponding IconGroup
            val iconGroup = icons[screen]
            val labelText = iconGroup?.label ?: "Unknown"

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = iconGroup?.let {
                            if (isSelected) it.filledIcon else it.outlineIcon
                        } ?: Icons.Default.Error, // Default icon if null
                        contentDescription = labelText
                    )
                },
                label = { Text(labelText) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination and reset the navigation stack
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
