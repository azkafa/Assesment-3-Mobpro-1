package org.d3if0020.assesment3mobpro.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.d3if0020.assesment3mobpro.database.AddressDao
import org.d3if0020.assesment3mobpro.data.Address

class DetailViewModel(private val dao: AddressDao) : ViewModel() {

    fun insert(label: String, alamat: String, type: String, isMain: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isMain) {
                dao.updateAllExceptToFalse()
            }

            val address = Address(
                label = label,
                alamat = alamat,
                type = type,
                isMain = isMain
            )

            dao.insert(address)
        }
    }

    suspend fun getAddress(id: Long): Address? {
        return dao.getAddressById(id)
    }

    suspend fun getMainAddress(): Address? {
        return dao.getMainAddress()
    }

    fun update(id: Long, label: String, alamat: String, type: String, isMain: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isMain) {
                dao.updateAllExceptToFalseExcept(id)
            }

            val address = Address(
                id = id,
                label = label,
                alamat = alamat,
                type = type,
                isMain = isMain
            )
            dao.update(address)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun getAllAddresses(): Flow<List<Address>> {
        return dao.getAddress()
    }
}
