package org.d3if0020.assesment2mobpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.d3if0020.assesment2mobpro.data.getPizzaById
import org.d3if0020.assesment2mobpro.screen.AboutScreen
import org.d3if0020.assesment2mobpro.screen.AddPizzaScreen
import org.d3if0020.assesment2mobpro.screen.AddressDetailScreen
import org.d3if0020.assesment2mobpro.screen.DeliveryAddressScreen
import org.d3if0020.assesment2mobpro.screen.KEY_ID_ADDRESS
import org.d3if0020.assesment2mobpro.screen.MainScreen
import org.d3if0020.assesment2mobpro.screen.PaymentMethodScreen
import org.d3if0020.assesment2mobpro.screen.PaymentScreen
import org.d3if0020.assesment2mobpro.screen.SettingScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Setting.route) {
            SettingScreen(navController)
        }
        composable(route = Screen.DeliveryAddress.route) {
            DeliveryAddressScreen(navController)
        }
        composable(route = Screen.PaymentMethod.route) {
            PaymentMethodScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            AddressDetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_ADDRESS) { type = NavType.LongType }
            )
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_ADDRESS)
            AddressDetailScreen(navController, id)
        }
        composable(route = Screen.AddPizza.route + "/{pizzaId}") { backStackEntry ->
            val pizzaId = backStackEntry.arguments?.getString("pizzaId")?.toIntOrNull()
            val selectedPizza = if (pizzaId != null) getPizzaById(pizzaId) else null
            AddPizzaScreen(navController, selectedPizza)
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


