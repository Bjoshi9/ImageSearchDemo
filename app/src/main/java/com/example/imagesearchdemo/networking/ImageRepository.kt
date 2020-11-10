package com.example.imagesearchdemo.networking

import androidx.lifecycle.MutableLiveData
import com.example.imagesearchdemo.model.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepository {

    companion object {
        private var imageRepository: ImageRepository? = null

        fun getInstance(): ImageRepository {
            if (imageRepository == null) {
                imageRepository = ImageRepository()
            }
            return imageRepository!!
        }
    }

    private var imageApi: ImageApi = RetrofitService.createService(ImageApi::class.java)

    fun getImage(searchText: String, pageCount: Int, imageData: MutableLiveData<ImageResponse?>) {
        val callback = imageApi.getImageList(pageCount, searchText)
        callback.enqueue(
            object : Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.isSuccessful) imageData.value = response.body()
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    imageData.value = null
                }
            }
        )
    }
}
