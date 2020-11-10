package com.example.imagesearchdemo.viewmodels

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearchdemo.model.ImageResponse;
import com.example.imagesearchdemo.networking.ImageRepository;

class ImageViewModel : ViewModel() {
    private lateinit var mutableLiveData: MutableLiveData<ImageResponse?>
    private lateinit var imageRepository: ImageRepository

    fun init() {
        imageRepository = ImageRepository.getInstance()
        mutableLiveData = MutableLiveData<ImageResponse?>()
        searchImage("",1)
    }

    fun searchImage(searchText: String, pageCount: Int) {
        imageRepository.getImage(searchText, pageCount, mutableLiveData)
    }

    fun getImageRepository(): LiveData<ImageResponse?> {
        return mutableLiveData
    }

}
