package org.d3if0020.assesment3mobpro.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.d3if0020.assesment3mobpro.data.Feedback
import org.d3if0020.assesment3mobpro.network.ApiStatus
import org.d3if0020.assesment3mobpro.network.FeedbackApi

class ApiViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Feedback>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }
    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = FeedbackApi.service.getFeedback()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}