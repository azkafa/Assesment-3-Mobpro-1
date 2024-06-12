package org.d3if0020.assesment3mobpro.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.d3if0020.assesment3mobpro.data.getPizzaById
import org.d3if0020.assesment3mobpro.model.DetailViewModel
import org.d3if0020.assesment3mobpro.model.UserViewModel
import org.d3if0020.assesment3mobpro.screen.AboutScreen
import org.d3if0020.assesment3mobpro.screen.AddPizzaScreen
import org.d3if0020.assesment3mobpro.screen.AddressDetailScreen
import org.d3if0020.assesment3mobpro.screen.DeliveryAddressScreen
import org.d3if0020.assesment3mobpro.screen.KEY_ID_ADDRESS
import org.d3if0020.assesment3mobpro.screen.MainScreen
import org.d3if0020.assesment3mobpro.screen.FeedbackScreen
import org.d3if0020.assesment3mobpro.screen.PaymentScreen
import org.d3if0020.assesment3mobpro.util.ViewModelFactory

@Composable
fun SetupNavGraph(navController: NavHostController, viewModelFactory: ViewModelFactory, userViewModel: UserViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController, userViewModel)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.DeliveryAddress.route) {
            DeliveryAddressScreen(navController)
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen(navController,userViewModel)
        }
        composable(route = Screen.FormBaru.route) {
            AddressDetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_ADDRESS) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_ADDRESS)
            AddressDetailScreen(navController, id)
        }
        composable(route = Screen.AddPizza.route + "/{pizzaId}") { backStackEntry ->
            val pizzaId = backStackEntry.arguments?.getString("pizzaId")?.toIntOrNull()
            val selectedPizza = if (pizzaId != null) getPizzaById(pizzaId) else null
            val viewModel: DetailViewModel = viewModel(factory = viewModelFactory)
            AddPizzaScreen(
                navController = navController,
                pizza = selectedPizza,
                viewModel = viewModel
            )
        }
        composable(route = Screen.Payment.route + "?totalHarga={totalHarga}&jumlahPesanan={jumlahPesanan}&topping={topping}&alamatPengiriman={alamatPengiriman}") { backStackEntry ->
            val totalHarga = backStackEntry.arguments?.getString("totalHarga")
            val jumlahPesanan = backStackEntry.arguments?.getString("jumlahPesanan")?.toIntOrNull()
            val topping = backStackEntry.arguments?.getString("topping")
            val alamatPengiriman = backStackEntry.arguments?.getString("alamatPengiriman")
            PaymentScreen(navController, totalHarga, jumlahPesanan, topping, alamatPengiriman)
        }
    }
}

