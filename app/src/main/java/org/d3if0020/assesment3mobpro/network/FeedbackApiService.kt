package org.d3if0020.assesment3mobpro.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if0020.assesment3mobpro.data.Feedback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.telutizen.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FeedbackApiService {
    @GET("azka")
    suspend fun getFeedback(): List<Feedback>
}

object FeedbackApi {
    val service: FeedbackApiService by lazy {
        retrofit.create(FeedbackApiService::class.java)
    }

    fun getFeedbackUrl(imageId: String): String {
        return "$BASE_URL$imageId"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }