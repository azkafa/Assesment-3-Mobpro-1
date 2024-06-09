package org.d3if0020.assesment3mobpro.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment3mobpro.R
import org.d3if0020.assesment3mobpro.ui.theme.OrderPizzaTheme


@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.tentang_aplikasi))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Text(
            text = stringResource(R.string.copyright),
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        )
    }
}




@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AboutScreenPreview() {
    OrderPizzaTheme {
        AboutScreen(rememberNavController())
    }
}