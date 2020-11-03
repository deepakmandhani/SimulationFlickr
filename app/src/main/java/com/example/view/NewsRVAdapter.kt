package com.example.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.common.Utils
import com.example.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item_news_layout.view.*

class NewsRVAdapter(private val context: Context, var list: List<Photo>) :
    RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_item_news_layout,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Photo>) {
        list = newList
        notifyDataSetChanged()
    }

    fun addData(newList: List<Photo>) {
        if (list.isNullOrEmpty())
            list = newList
        else
            (list as MutableList<Photo>).addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val obj = list[position]
            obj.apply {
                itemView.tv_title.text = title

//                var imgUrl = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_m.jpg"
                val imgUrl = Utils.FLICKR_PHOTO_URL
                    .replace("KEY1", farm.toString())
                    .replace("KEY2", server)
                    .replace("KEY3", id)
                    .replace("KEY4", secret)

                Picasso.get().load(imgUrl).noFade().fit().centerCrop()
                    .placeholder(R.drawable.download)
                    .error(R.drawable.errr)
                    .into(itemView.iv_photo)
            }
        }
    }
}