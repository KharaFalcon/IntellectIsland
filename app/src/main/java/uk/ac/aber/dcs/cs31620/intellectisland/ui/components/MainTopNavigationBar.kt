import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.faa.R


@Composable
fun MainTopNavigationBar(
    onClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.nav_drawer_menu)
                )
            }
        }
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            painter = painterResource(id = R.drawable.profile_icon),
            contentDescription = stringResource(R.string.profile_icon_image),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun MainTopNavigationBarPreview() {
    MainTopNavigationBar()
}
