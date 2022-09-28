package com.example.smaicle

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.time.Duration

interface RetrofitClient {
    @Multipart
    @POST("image")
    suspend fun getLabelledImage(@Part filePart: MultipartBody.Part): Response<ResponseBody>
//    fun getLabelledImage(@Part filePart: MultipartBody.Part): Call<ResponseBody>
}

object RetrofitService {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getClient(): RetrofitClient {
        val baseUrl = "http://192.168.0.122:5000"
        val gson = GsonConverterFactory.create()
        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl).client(OkHttpClient.Builder()
                .readTimeout(Duration.ofSeconds(30))
                .writeTimeout(Duration.ofSeconds(30))
                .callTimeout(Duration.ofSeconds(30))
                .connectTimeout(Duration.ofSeconds(30)).build())
                .addConverterFactory(gson)
                .build()
        return retrofit.create(RetrofitClient::class.java)
    }
}