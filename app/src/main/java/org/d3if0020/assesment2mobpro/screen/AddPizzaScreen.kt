package org.d3if0020.assesment2mobpro.screen

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment2mobpro.R
import org.d3if0020.assesment2mobpro.data.Pizza
import org.d3if0020.assesment2mobpro.data.Topping
import org.d3if0020.assesment2mobpro.data.pizzaList
import org.d3if0020.assesment2mobpro.navigation.Screen
import org.d3if0020.assesment2mobpro.ui.theme.PizzaHutAppTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


@Composable
fun AddPizzaScreen(navController: NavHostController, pizza: Pizza?) {
    val pizzaQuantity = rememberSaveable { mutableIntStateOf(pizza?.quantity ?: 0) }
    val selectedTopping = rememberSaveable { mutableStateOf<Topping?>(null) }
    val address = rememberSaveable { mutableStateOf("") }
    val showResult = rememberSaveable { mutableStateOf(false) }
    val resultMessage = rememberSaveable { mutableStateOf("") }
    val errorMessage = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    fun calculateTotalPrice(): Float {
        var totalPrice = 0f
        pizza?.price?.let { priceString ->
            val numericPart = priceString.substringAfter("Rp.").replace(".", "")
            totalPrice = numericPart.toFloat() * pizzaQuantity.intValue
        }
        return totalPrice
    }

    fun formatHarga(harga: Float): String {
        val symbols = DecimalFormatSymbols(Locale.getDefault())
        symbols.groupingSeparator = '.'
        val formatter = DecimalFormat("#,###", symbols)
        return "Rp. ${formatter.format(harga)}"
    }

    fun validateInput(context: Context) {
        when {
            pizzaQuantity.intValue <= 0 -> {
                errorMessage.value = context.getString(R.string.error_quantity_zero)
            }
            selectedTopping.value == null -> {
                errorMessage.value = context.getString(R.string.error_no_topping)
            }
            address.value.isEmpty() -> {
                errorMessage.value = context.getString(R.string.error_empty_address)
            }
            else -> {
                errorMessage.value = ""
                val totalPrice = calculateTotalPrice()
                val deliveryAddress = address.value
                val formattedTotalPrice = formatHarga(totalPrice)
                resultMessage.value = context.getString(
                    R.string.result_message,
                    pizzaQuantity.intValue,
                    selectedTopping.value?.name ?: context.getString(R.string.no_topping),
                    deliveryAddress,
                    formattedTotalPrice
                )
                showResult.value = true
            }
        }
    }

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
                    Text(text = pizza?.name ?: stringResource(id = R.string.add_pizza))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primary
            )
        }

    ) { padding ->
        pizza?.let { selectedPizza ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = selectedPizza.image),
                    contentDescription = stringResource(R.string.pizza_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = selectedPizza.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = selectedPizza.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = selectedPizza.price,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .border(1.dp, Color.Gray)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.jumlah_pizza),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Button(
                        onClick = {
                            if (pizzaQuantity.intValue > 0) {
                                pizzaQuantity.intValue--
                            }
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text("-")
                    }
                    Text(
                        text = pizzaQuantity.intValue.toString(),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(40.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = {
                            pizzaQuantity.intValue++
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text("+")
                    }
                }

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp))

                Column {
                    Text(
                        text = stringResource(R.string.topping),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Topping.entries.forEach { topping ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = topping.name,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioButton(
                                selected = selectedTopping.value == topping,
                                onClick = { selectedTopping.value = topping },
                                colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary)
                            )
                            if (selectedTopping.value != topping && errorMessage.value.isNotEmpty()) {
                                IconPicker(showIcon = true, message = "Error Icon")
                            }
                        }
                    }
                }

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp))

                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text(text = stringResource(R.string.alamat))},
                    isError = errorMessage.value.isNotEmpty(),
                    trailingIcon = {
                        IconPicker(errorMessage.value.isNotEmpty(), "pesan error")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { validateInput(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = stringResource(R.string.add_2),
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = Color.Red,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                if (showResult.value) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colors.primaryVariant,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = resultMessage.value,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate("${Screen.Payment.route}?totalHarga=${calculateTotalPrice()}&jumlahPesanan=${pizzaQuantity.intValue}&topping=${selectedTopping.value?.name ?: ""}&alamatPengiriman=${address.value}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.bayar),
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconPicker(showIcon: Boolean, message: String) {
    if (showIcon) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = message,
            tint = Color.Red
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddPizzaScreenPreview() {
    PizzaHutAppTheme {
        val pizza = pizzaList.first()
        AddPizzaScreen(rememberNavController(), pizza)
    }
}
