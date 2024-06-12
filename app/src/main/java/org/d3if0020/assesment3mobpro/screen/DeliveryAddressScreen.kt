package org.d3if0020.assesment3mobpro.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0020.assesment3mobpro.R
import org.d3if0020.assesment3mobpro.database.AddressDb
import org.d3if0020.assesment3mobpro.data.Address
import org.d3if0020.assesment3mobpro.model.MainViewModel
import org.d3if0020.assesment3mobpro.navigation.Screen
import org.d3if0020.assesment3mobpro.ui.theme.OrderPizzaTheme
import org.d3if0020.assesment3mobpro.util.SettingsDataStore
import org.d3if0020.assesment3mobpro.util.ViewModelFactory

@Composable
fun DeliveryAddressScreen(navController: NavHostController) {

    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.address))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                },
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.tambah_alamat),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { padding ->
        ScreenContent(showList, Modifier.padding(padding), navController)
    }
}



    @Composable
fun ScreenContent(showList: Boolean, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = AddressDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.warning2),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.list_alamat_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(address = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                    Divider()
                }
            }
        }
        else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(address = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    address: Address,
    onClick: () -> Unit,
) {
    val primaryColor = MaterialTheme.colors.primary
    val contentColor = MaterialTheme.colors.onPrimary
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = address.label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = address.alamat,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = primaryColor,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = address.type,
                        color = contentColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                if (address.isMain) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = primaryColor,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Alamat Utama",
                            color = contentColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(address: Address, onClick: () -> Unit) {

    val primaryColor = MaterialTheme.colors.primary
    val contentColor = MaterialTheme.colors.onPrimary
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(1.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = address.label,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = address.alamat,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = primaryColor,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = address.type,
                        color = contentColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                if (address.isMain) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = primaryColor,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Alamat Utama",
                            color = contentColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DeliverAddressPreview() {
    OrderPizzaTheme {
        DeliveryAddressScreen(rememberNavController())
    }
}