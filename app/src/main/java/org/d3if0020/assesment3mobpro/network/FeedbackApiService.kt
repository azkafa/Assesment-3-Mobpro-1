package org.d3if0020.assesment3mobpro.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0020.assesment3mobpro.data.Feedback
import org.d3if0020.assesment3mobpro.data.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://api.azkafa.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FeedbackApiService {
    @GET("azka.php")
    suspend fun getFeedback(
        @Query("auth") userId: String
    ): List<Feedback>

    @Multipart
    @POST("azka.php")
    suspend fun postFeedback(
        @Part("auth") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("azka.php")
    suspend fun deleteFeedback(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object FeedbackApi {
    val service: FeedbackApiService by lazy {
        retrofit.create(FeedbackApiService::class.java)
    }

    fun getFeedbackUrl(imageId: String): String {
        return "${BASE_URL}$imageId"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }