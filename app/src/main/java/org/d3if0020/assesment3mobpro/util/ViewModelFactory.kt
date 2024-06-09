package org.d3if0020.assesment3mobpro.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0020.assesment3mobpro.database.AddressDao
import org.d3if0020.assesment3mobpro.model.DetailViewModel
import org.d3if0020.assesment3mobpro.model.MainViewModel

class ViewModelFactory(private val dao: AddressDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}