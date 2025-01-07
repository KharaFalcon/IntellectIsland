package uk.ac.aber.dcs.cs31620.intellectisland.ui.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.Edit
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
        Screen.AddQuestions to IconGroup(
            filledIcon = Icons.Filled.Edit,
            outlineIcon = Icons.Outlined.Edit,
            label = stringResource(R.string.management_mode)
        )
    )

    val screens = listOf(Screen.HomeScreen, Screen.StartQuiz, Screen.AddQuestions)

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val iconGroup = icons[screen]

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            iconGroup?.filledIcon ?: Icons.Default.Error
                        } else {
                            iconGroup?.outlineIcon ?: Icons.Default.Error
                        },
                        contentDescription = iconGroup?.label ?: "Unknown"
                    )
                },
                label = { Text(iconGroup?.label ?: "Unknown") },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
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
