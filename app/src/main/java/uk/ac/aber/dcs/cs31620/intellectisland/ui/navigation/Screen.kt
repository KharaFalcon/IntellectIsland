package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation


sealed class Screen(
    val route: String
) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Add : Screen("add")
    object Edit : Screen("edit")
    object Delete : Screen("delete")
}

val screens = listOf(
    Screen.Home,
    Screen.Login,
    Screen.Add,
    Screen.Edit,
    Screen.Delete
)