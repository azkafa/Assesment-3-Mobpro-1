package org.d3if0020.assesment2mobpro.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.d3if0020.assesment2mobpro.database.AddressDao
import org.d3if0020.assesment2mobpro.data.Address
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: AddressDao) : ViewModel() {

    val data: StateFlow<List<Address>> = dao.getAddress().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}