package org.d3if0020.assesment2mobpro.navigation

import org.d3if0020.assesment2mobpro.screen.KEY_ID_ADDRESS

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object AddPizza: Screen("addPizzaScreen")
    data object Payment: Screen("paymentScreen")
    data object Setting: Screen("settingScreen")
    data object DeliveryAddress: Screen("deliveryAddressScreen")
    data object PaymentMethod: Screen("paymentMethodScreen")
    data object FormBaru : Screen("addressDetailScreen")
    data object FormUbah: Screen("addressDetailScreen/{$KEY_ID_ADDRESS}") {
        fun withId(id: Long) = "addressDetailScreen/$id"
    }
}
