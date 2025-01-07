package uk.ac.aber.dcs.cs31620.intellectisland.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.intellectisland.R
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.intellectisland.ui.components.MainTopNavigationBar

@Composable
fun UserProfile(navController: NavHostController) {
    val defaultPictures = listOf(
        R.drawable.av1,
        R.drawable.av2,
        R.drawable.av3,
        R.drawable.av4,
        R.drawable.av5,
        R.drawable.av6,
        R.drawable.av7,
        R.drawable.av8,
        R.drawable.av9
    )

    val coroutineScope = rememberCoroutineScope()

    // States for selected picture, name, and other details
    var selectedPicture by remember { mutableStateOf(defaultPictures[0]) }
    var userName by remember { mutableStateOf("John Doe") }

    TopLevelScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        pageContent = { innerPadding ->

            MainTopNavigationBar(
                navController = navController,

            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 80.dp)
                )

                // Profile Picture Section
                Image(
                    painter = painterResource(id = selectedPicture),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable {
                            coroutineScope.launch {
                                Toast
                                    .makeText(
                                        navController.context,
                                        "Change profile picture clicked",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        },
                    contentScale = ContentScale.Crop
                )

                // Editable Name and Email Fields
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Picture Selection Row
                Text(
                    text = "Choose Profile Picture",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    defaultPictures.forEach { picture ->
                        Image(
                            painter = painterResource(id = picture),
                            contentDescription = "Default Picture",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable {
                                    selectedPicture = picture
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {
                        coroutineScope.launch {
                            Toast.makeText(
                                navController.context,
                                "Saving changes",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Add your save logic here, e.g., make a network call or database operation
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save Changes",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    )
}
