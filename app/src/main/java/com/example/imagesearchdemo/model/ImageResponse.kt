package com.example.imagesearchdemo.model

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageResponse {

   @SerializedName("data")
    @Expose
    lateinit var data: ArrayList<ImageItem>
}
