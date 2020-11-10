package com.example.imagesearchdemo.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchdemo.R
import com.example.imagesearchdemo.adapters.ImageAdapter
import com.example.imagesearchdemo.model.ImageItem
import com.example.imagesearchdemo.viewmodels.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var imageList: MutableList<ImageItem> = ArrayList()
    private lateinit var imageViewModel: ImageViewModel
    private var imageAdapter: ImageAdapter? = null
    private var mIsLoading = false
    private var mSearchText: String = ""
    private var pageCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel::class.java)
        imageViewModel.init()
        imageViewModel.getImageRepository().observe(this) { imageResponse ->
            mIsLoading = false
            imageResponse?.let {
                Log.d("RES", imageResponse.data.size.toString())
                if (pageCount == 1) {
                    imageList.clear()
                    imageAdapter?.notifyDataSetChanged()
                }
                imageList.addAll(imageResponse.data)
                imageAdapter?.notifyDataSetChanged()
                progressBottom.visibility = View.GONE
            }
        }
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter(this@MainActivity, imageList,
            object : ImageAdapter.OnImageItemClickListener {
                override fun onImageItemClick(adapterPosition: Int) {
                    val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    val imageItem = imageList[adapterPosition]
                    intentDetail.putExtra("Title", imageItem.title)
                    intentDetail.putExtra("ImageLink", imageItem.images[0].link)
                    startActivity(intentDetail)
                }

            })
        rvImages.apply {

            val span =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

            layoutManager = GridLayoutManager(this@MainActivity, span)
            adapter = imageAdapter
            itemAnimator = DefaultItemAnimator()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (mIsLoading)
                        return
                    val visibleItemCount = recyclerView.layoutManager?.childCount!!
                    val totalItemCount = recyclerView.layoutManager?.itemCount!!
                    val pastVisibleItems =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        pageCount++
                        getImages()
                    }
                }
            })
        }

    }

    var queryTextChangedJob: Job? = null

    private fun setupSearchView() {
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String?): Boolean {
                queryText?.let {
                    mSearchText = it
                    pageCount = 1
                    getImages()
                }
                return true
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                queryText?.let {
                    queryTextChangedJob?.cancel()
                    queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(250)
                        mSearchText = it
                        pageCount = 1
                        getImages()
                    }
                }
                return true
            }

        })
    }

    private fun getImages() {
        mIsLoading = true
        imageViewModel.searchImage(mSearchText, pageCount)
        progressBottom.visibility = View.VISIBLE
    }
}