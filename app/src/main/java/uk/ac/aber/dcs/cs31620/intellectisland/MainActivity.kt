package uk.ac.aber.dcs.cs31620.intellectIsland

import MainTopNavigationBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.IntellectIslandTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntellectIslandTheme(dynamicColor = false) {
MainTopNavigationBar()
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntellectIslandTheme {
        //MainTopNavigationBar
    }
}