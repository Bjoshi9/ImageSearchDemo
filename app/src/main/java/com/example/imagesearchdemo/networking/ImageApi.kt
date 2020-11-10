package com.example.imagesearchdemo.networking

import com.example.imagesearchdemo.model.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ImageApi {
    @GET("3/gallery/search/{pageCount}")
    @Headers(
        "Authorization: Client-ID 137cda6b5008a7c",
    )
    fun getImageList(
        @Path("pageCount") pageCount: Int,
        @Query("q") query: String
    ): Call<ImageResponse>
}
