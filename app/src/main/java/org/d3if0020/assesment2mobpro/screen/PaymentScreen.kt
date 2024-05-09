package org.d3if0020.assesment2mobpro.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment2mobpro.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.text.NumberFormat
import java.util.Locale


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StringFormatInvalid")
@Composable
fun PaymentScreen(
    navController: NavHostController,
    totalHarga: String?,
    jumlahPesanan: Int?,
    topping: String?,
    alamatPengiriman: String?
) {
    val context = LocalContext.current
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
                    Text(text = stringResource(id = R.string.payment))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primary
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Terima kasih sudah melakukan pembayaran",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            Text(
                text = stringResource(R.string.total),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = formatHarga(totalHarga),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            Text(
                text = stringResource(R.string.jumlah_pizza),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = jumlahPesanan?.toString() ?: "",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            Text(
                text = stringResource(R.string.topping_2),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = topping ?: "",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            Text(
                text = stringResource(R.string.alamat),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = alamatPengiriman ?: "",
                style = MaterialTheme.typography.body2
            )
            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        shareData(
                            context = context,
                            message = context.getString(
                                R.string.share_message_template,
                                formatHarga(totalHarga),
                                jumlahPesanan,
                                topping,
                                alamatPengiriman
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.share),
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private fun formatHarga(harga: String?): String {
    return if (!harga.isNullOrEmpty()) {
        val hargaInt = harga.toFloatOrNull()
        val formattedHarga = hargaInt?.let {
            val formatter = NumberFormat.getInstance(Locale.getDefault())
            val formattedValue = formatter.format(it)
            "Rp. $formattedValue"
        } ?: ""
        formattedHarga
    } else {
        ""
    }
}

@SuppressLint("QueryPermissionsNeeded")
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PaymentScreenPreview() {
    val navController = rememberNavController()
    PaymentScreen(navController, null, null, null, null)
}

