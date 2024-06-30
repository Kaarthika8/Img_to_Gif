package com.example.animatedgif

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface GifApi {
    @Multipart
    @POST("jpg/gif")
    fun uploadImageToGif(
        @Part image: MultipartBody.Part
    ): Call<GifResponse>
}

data class GifResponse(
    val gifUrl: String
)

