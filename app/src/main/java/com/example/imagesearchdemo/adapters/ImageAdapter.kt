package com.example.imagesearchdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.imagesearchdemo.R
import com.example.imagesearchdemo.model.ImageItem
import kotlinx.android.synthetic.main.image_item.view.*

class ImageAdapter(private var context: Context?, private var articles: MutableList<ImageItem>, private val onImageItemClickListener: OnImageItemClickListener) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (articles[position].imageCount > 0)
            Glide
                .with(holder.itemView.ivImage)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_launcher_foreground)
                )

                .load(articles[position].images[0].link)
                .into(holder.itemView.ivImage)

        holder.itemView.setOnClickListener {
            onImageItemClickListener.onImageItemClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnImageItemClickListener {
        fun onImageItemClick(adapterPosition: Int)
    }
}
