package org.d3if0020.assesment3mobpro.screen


import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment3mobpro.R
import org.d3if0020.assesment3mobpro.commonui.SpacerHeight
import org.d3if0020.assesment3mobpro.data.Pizza
import org.d3if0020.assesment3mobpro.data.pizzaList
import org.d3if0020.assesment3mobpro.navigation.Screen
import org.d3if0020.assesment3mobpro.ui.theme.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    val menuList = listOf("All", "Starter", "Asian", "Classic")
    val scrollState = rememberScrollState()
    var menuState by rememberSaveable { mutableStateOf("All") }

    val filteredPizzaList = if (menuState == "All") {
        pizzaList
    } else {
        pizzaList.filter { it.category == menuState }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.username),
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.White
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.horizontalScroll(scrollState)
                ) {
                    menuList.forEach { it ->
                        MenuItems(menuName = it, selected = it == menuState) {
                            menuState = it
                        }
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(filteredPizzaList) { pizza ->
                    ShowPizza(
                        pizza = pizza,
                        navController = navController
                    )
                }
            }
        }
    }
}




@Composable
fun MenuItems(
    menuName: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onValueUpdate: (String) -> Unit
) {
    TextButton(
        onClick = { onValueUpdate(menuName) },
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) YellowColor else Color.Transparent
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 5.dp)
    ) {
        Text(
            text = menuName,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                color = if (selected) Color.White else DarkBlackColor
            ),
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.home, Screen.Home.route),
        BottomNavItem("Delivery", R.drawable.location, Screen.DeliveryAddress.route),
        BottomNavItem("Feedback", R.drawable.feedback, Screen.Feedback.route),
        BottomNavItem("About", R.drawable.info, Screen.About.route)
    )

    val topAppBarColor = MaterialTheme.colors.primary

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.name,
                        modifier = Modifier.size(24.dp),
                        tint = if (currentRoute == item.route) topAppBarColor else Color.Gray
                    )
                },
                label = {
                    Text(text = item.name, color = if (currentRoute == item.route) topAppBarColor else Color.Gray)
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
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
data class BottomNavItem(val name: String, val icon: Int, val route: String)


@Composable
fun ShowPizza(
    pizza: Pizza,
    navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .width(175.dp)
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = pizza.image), contentDescription = "",
                    modifier = Modifier.size(109.dp)
                )
                SpacerHeight(5.dp)
                Text(
                    text = pizza.price, style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = RedColor
                    ),
                    textAlign = TextAlign.Center
                )
                SpacerHeight(5.dp)
                Text(
                    text = pizza.name, style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        color = DarkBlackColor
                    ),
                    textAlign = TextAlign.Center
                )
                SpacerHeight(5.dp)
                Text(
                    text = pizza.description, style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W300,
                        color = LightGrayColor
                    ),
                    textAlign = TextAlign.Center
                )
                SpacerHeight(5.dp)
                Button(
                    onClick = {
                        navController.navigate(Screen.AddPizza.route + "/${pizza.id}")
                    },
                    modifier = Modifier
                        .width(91.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = YellowColor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.add), style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    OrderPizzaTheme {
        MainScreen(rememberNavController())
    }
}