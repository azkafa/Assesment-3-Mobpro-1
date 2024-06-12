package org.d3if0020.assesment3mobpro.alert


import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if0020.assesment3mobpro.R
import org.d3if0020.assesment3mobpro.ui.theme.OrderPizzaTheme

@Composable
fun DeleteConfirmationDialog(onDismissRequest: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(id = R.string.confirm_delete))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    OutlinedButton(
                        onClick = { onConfirm() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DeleteConfirmationDialogPreview() {
    OrderPizzaTheme {
        DeleteConfirmationDialog(
            onDismissRequest = {},
            onConfirm = {}
        )
    }
}