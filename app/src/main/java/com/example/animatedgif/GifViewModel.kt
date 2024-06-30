package com.example.animatedgif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class GifViewModel : ViewModel() {

    private val _gifUrl = MutableLiveData<String?>()
    val gifUrl: MutableLiveData<String?> get() = _gifUrl

    private val api: GifApi = RetrofitClient.instance.create(GifApi::class.java)

    fun uploadImageToGif(imageFile: File) {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

        api.uploadImageToGif(body).enqueue(object : Callback<GifResponse> {
            override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                if (response.isSuccessful) {
                    val gifUrl = response.body()?.gifUrl
                    _gifUrl.value = gifUrl
                } else {
                    // Handle unsuccessful response
                    _gifUrl.value = null
                }
            }

            override fun onFailure(call: Call<GifResponse>, t: Throwable) {
                // Handle failure
                _gifUrl.value = null
            }
        })
    }
}
