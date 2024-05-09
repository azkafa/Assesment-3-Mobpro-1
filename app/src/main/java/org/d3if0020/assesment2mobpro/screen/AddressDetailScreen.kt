package org.d3if0020.assesment2mobpro.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0020.assesment2mobpro.R
import org.d3if0020.assesment2mobpro.alert.DisplayAlertDialog
import org.d3if0020.assesment2mobpro.database.AddressDb
import org.d3if0020.assesment2mobpro.model.DetailViewModel
import org.d3if0020.assesment2mobpro.ui.theme.PizzaHutAppTheme
import org.d3if0020.assesment2mobpro.util.ViewModelFactory

const val KEY_ID_ADDRESS = "idAddress"
@Composable
fun AddressDetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = AddressDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var label by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var isMain by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getAddress(id) ?: return@LaunchedEffect
        label = data.label
        alamat = data.alamat
        type = data.type
        isMain = data.isMain
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
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_alamat))
                    else
                        Text(text = stringResource(id = R.string.edit_alamat))
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primary,

                actions = {
                    IconButton(onClick = {
                        if (label == "" || alamat == "" || type == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(label, alamat, type, isMain)
                        } else {
                            viewModel.update(id, label, alamat, type, isMain)
                        }
                        navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = Color.White
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormAlamat(
                nama = label,
                onNamaChange = { label = it },
                alamat = alamat,
                onAlamatChange = { alamat = it },
                type = type,
                onTypeChange = { type = it },
                isMain = isMain,
                onMainChange = { isMain = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FormAlamat(
    nama: String, onNamaChange: (String) -> Unit,
    alamat: String, onAlamatChange: (String) -> Unit,
    type: String, onTypeChange: (String) -> Unit,
    isMain: Boolean, onMainChange: (Boolean) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { onNamaChange(it) },
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alamat,
            onValueChange = { onAlamatChange(it) },
            label = { Text(text = stringResource(R.string.alamatt)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TypeDropdown(
            type = type,
            onTypeChange = onTypeChange
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.tandai_alamat_utama),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            ToggleButton(
                checked = isMain,
                onCheckedChange = onMainChange
            )
        }
    }
}



@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier
    )
}


@Composable
fun TypeDropdown(type: String, onTypeChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = type,
            onValueChange = {},
            label = { Text(text = stringResource(R.string.dropdown)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true }),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.expand_dropdown),
                    modifier = Modifier.clickable(onClick = { expanded = !expanded })
                )
            }
        )
        if (expanded) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    DropdownMenuItem(onClick = {
                        onTypeChange("Rumah")
                        expanded = false
                    }) {
                        Text(text = "Rumah")
                    }
                    DropdownMenuItem(onClick = {
                        onTypeChange("Kantor")
                        expanded = false
                    }) {
                        Text(text = "Kantor")
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    delete()
                }
            ) {
                Text(text = stringResource(id = R.string.hapus))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddressDetailScreenPreview() {
    PizzaHutAppTheme {
        AddressDetailScreen(rememberNavController())
    }
}