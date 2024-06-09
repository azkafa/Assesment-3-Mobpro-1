package org.d3if0020.assesment3mobpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment3mobpro.database.AddressDb
import org.d3if0020.assesment3mobpro.navigation.SetupNavGraph
import org.d3if0020.assesment3mobpro.ui.theme.OrderPizzaTheme
import org.d3if0020.assesment3mobpro.util.ViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the database and get the AddressDao
        val database = AddressDb.getInstance(this)
        val addressDao = database.dao
        val viewModelFactory = ViewModelFactory(addressDao)

        setContent {
            val navController = rememberNavController()
            OrderPizzaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SetupNavGraph(navController = navController, viewModelFactory = viewModelFactory)
                }
            }
        }
    }
}