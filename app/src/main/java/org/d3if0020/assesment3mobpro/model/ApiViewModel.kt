package org.d3if0020.assesment3mobpro.model

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if0020.assesment3mobpro.data.Feedback
import org.d3if0020.assesment3mobpro.network.ApiStatus
import org.d3if0020.assesment3mobpro.network.FeedbackApi
import java.io.ByteArrayOutputStream

class ApiViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Feedback>())
        private set
    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = FeedbackApi.service.getFeedback(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("ApiViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, nama: String, deskripsi: String, rating: String,bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = FeedbackApi.service.postFeedback(
                    userId,
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    deskripsi.toRequestBody("text/plain".toMediaTypeOrNull()),
                    rating.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )

                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("ApiViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteData(userId: String, feedbackId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("ApiViewModel", "Attempting to delete feedback with ID: $feedbackId using user ID: $userId")
                val result = FeedbackApi.service.deleteFeedback(userId, feedbackId)
                Log.d("ApiViewModel", "API Response: status=${result.status}, message=${result.message}")
                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("ApiViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }
}
