package org.d3if0020.assesment3mobpro.screen

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import org.d3if0020.assesment3mobpro.BuildConfig
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.d3if0020.assesment3mobpro.R
import org.d3if0020.assesment3mobpro.commonui.SpacerHeight
import org.d3if0020.assesment3mobpro.data.Pizza
import org.d3if0020.assesment3mobpro.data.User
import org.d3if0020.assesment3mobpro.data.pizzaList
import org.d3if0020.assesment3mobpro.navigation.Screen
import org.d3if0020.assesment3mobpro.network.UserDataStore
import org.d3if0020.assesment3mobpro.ui.theme.*
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import org.d3if0020.assesment3mobpro.model.UserViewModel


@Composable
fun MainScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())

    var showDialog by remember { mutableStateOf(false) }

    val menuList = listOf("All", "Starter", "Asian", "Classic")
    val scrollState = rememberScrollState()
    var menuState by rememberSaveable { mutableStateOf("All") }

    val filteredPizzaList = if (menuState == "All") {
        pizzaList
    } else {
        pizzaList.filter { it.category == menuState }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.username),
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
                    .fillMaxWidth(),
                actions = {
                    IconButton(onClick = {
                        if (user.email.isEmpty()) {
                            CoroutineScope(Dispatchers.IO).launch { signIn(context, dataStore, userViewModel) }
                        } else {
                            showDialog = true
                        }
                    }) {
                        if (user.photoUrl.isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(
                                        LocalContext.current
                                    ).data(data = user.photoUrl).apply(block = fun ImageRequest.Builder.() {
                                        transformations(CircleCropTransformation())
                                    }).build()
                                ),
                                contentDescription = stringResource(R.string.profil),
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.account_circle_24),
                                contentDescription = stringResource(R.string.profil),
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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

        if (showDialog) {
            ProfilDialog(
                user = user,
                onDismissRequest = { showDialog = false }) {
                CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStore, userViewModel) }
                showDialog = false
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

private suspend fun signIn(context: Context, dataStore: UserDataStore, userViewModel: UserViewModel) {
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.API_KEY)
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val credentialManager = CredentialManager.create(context)
        val result = credentialManager.getCredential(context, request)
        handleSignIn(result, dataStore, userViewModel)
    } catch (e: GetCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
    }
}

private suspend fun handleSignIn(result: GetCredentialResponse, dataStore: UserDataStore, userViewModel: UserViewModel) {
    val credential = result.credential
    if (credential is CustomCredential &&
        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        try {
            val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
            val nama = googleIdToken.displayName ?: ""
            val email = googleIdToken.id
            val photoUrl = googleIdToken.profilePictureUri.toString()
            val newUser = User(nama, email, photoUrl)
            dataStore.saveData(newUser)
            userViewModel.updateUser(newUser)
        } catch (e: GoogleIdTokenParsingException) {
            Log.e("SIGN-IN", "Error: ${e.message}")
        }
    } else {
        Log.e("SIGN-IN", "Error: unrecognized custom credential type.")
    }
}

private suspend fun signOut(context: Context, dataStore: UserDataStore, userViewModel: UserViewModel) {
    try {
        val credentialManager = CredentialManager.create(context)
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        val newUser = User()
        dataStore.saveData(newUser)
        userViewModel.updateUser(newUser)
    } catch (e: ClearCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val userViewModel = UserViewModel()
    OrderPizzaTheme {
        MainScreen(navController, userViewModel)
    }
}