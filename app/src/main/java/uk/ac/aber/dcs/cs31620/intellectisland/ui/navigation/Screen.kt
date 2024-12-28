package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation


sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("homeScreen")
    object Login : Screen("login")
    object Add : Screen("add")
    object Edit : Screen("edit")
    object Delete : Screen("delete")
    object StartQuiz : Screen("startQuiz")
    object Question : Screen("question")
    object AddQuestions : Screen("addQuestions")
}

val screens = listOf(
    Screen.HomeScreen,
    Screen.Login,
    Screen.AddQuestions,
    Screen.Edit,
    Screen.Delete,
    Screen.StartQuiz,
    Screen.Question,

)