package org.d3if0020.assesment2mobpro.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment2mobpro.R
import org.d3if0020.assesment2mobpro.navigation.Screen
import org.d3if0020.assesment2mobpro.ui.theme.PizzaHutAppTheme


@Composable
fun SettingScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.settings))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primary
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            SettingItem(
                title = stringResource(R.string.delivery_address),
                icon = R.drawable.delivery,
                onClick = { navController.navigate(Screen.DeliveryAddress.route) }
            )
            SettingItem(
                title = stringResource(R.string.payment_method),
                icon = R.drawable.payment,
                onClick = { navController.navigate(Screen.PaymentMethod.route) }
            )
            SettingItem(
                title = stringResource(R.string.tentang_aplikasi),
                icon = R.drawable.info,
                onClick = { navController.navigate(Screen.About.route) }
            )
        }
    }
}

@Composable
fun SettingItem(title: String, icon: Int, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Icon",
            modifier = Modifier.size(24.dp)
                .align(Alignment.Top)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Forward",
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.Top)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SettingScreenPreview() {
    PizzaHutAppTheme {
        SettingScreen(rememberNavController())
    }
}