package org.d3if0020.assesment3mobpro.navigation

import org.d3if0020.assesment3mobpro.screen.KEY_ID_ADDRESS

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object AddPizza: Screen("addPizzaScreen")
    data object Payment: Screen("paymentScreen")
    data object DeliveryAddress: Screen("deliveryAddressScreen")
    data object Feedback: Screen("feedbackScreen")
    data object FormBaru : Screen("addressDetailScreen")
    data object FormUbah: Screen("addressDetailScreen/{$KEY_ID_ADDRESS}") {
        fun withId(id: Long) = "addressDetailScreen/$id"
    }
}
