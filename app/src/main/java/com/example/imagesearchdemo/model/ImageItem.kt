package com.example.imagesearchdemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageItem {

    @SerializedName("title")
    @Expose
    lateinit var title: String

    @Expose
    @SerializedName("images_count")
    var imageCount : Int = 0

    @SerializedName("images")
    @Expose
    lateinit var images: MutableList<Image>

    inner class Image {
        @Expose
        @SerializedName("link")
        lateinit var link : String
    }
}

