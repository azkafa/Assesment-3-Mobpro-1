package org.d3if0020.assesment3mobpro.alert

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3if0020.assesment3mobpro.R
import org.d3if0020.assesment3mobpro.ui.theme.OrderPizzaTheme

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            shape = RoundedCornerShape(16.dp),
            text = { Text(text = stringResource(R.string.pesan_hapus)) },
            confirmButton = {
                TextButton(onClick = { onConfirmation() }) {
                    Text(text = stringResource(R.string.tombol_hapus))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.tombol_batal))
                }
            },
            onDismissRequest = { onDismissRequest() }
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    OrderPizzaTheme {
        DisplayAlertDialog(
            openDialog = true,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}