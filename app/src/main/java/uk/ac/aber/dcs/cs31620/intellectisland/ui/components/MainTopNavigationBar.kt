package uk.ac.aber.dcs.cs31620.intellectisland.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.intellectisland.R
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.IntellectIslandTheme
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.onSurfaceLight
import uk.ac.aber.dcs.cs31620.intellectisland.ui.theme.primaryContainerLight

@Composable
fun MainTopNavigationBar(navController: NavHostController, onClick: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = primaryContainerLight,
            titleContentColor = onSurfaceLight,
            navigationIconContentColor = onSurfaceLight
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title content can go here if needed
            }
        },
        navigationIcon = {
            // Image outside the Row, acting as a navigation icon
            Image(
                modifier = Modifier
                    .size(260.dp)
                    .padding(start = 150.dp),
                painter = painterResource(id = R.drawable.nav_img),
                contentDescription = stringResource(R.string.nav_image), // Correct content description
            )
            Image(
                modifier = Modifier
                    .size(380.dp) // Size of the profile icon
                    .clip(CircleShape) // Circular profile image
                    .padding(start = 340.dp, top = 10.dp, bottom = 10.dp) // Padding from the right edge
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                painter = painterResource(id = R.drawable.profile_icon), // Replace with your profile image resource
                contentDescription = stringResource(R.string.profile_icon_image),
                contentScale = ContentScale.Crop,// Correct content description
            )
        },

        actions = {
            // Add a profile icon on the right side
            DropDownMenu(navController, onClick = onClick)
        }
    )
}

@Preview
@Composable
private fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    IntellectIslandTheme(dynamicColor = false) {
        MainTopNavigationBar(navController)
    }
}
