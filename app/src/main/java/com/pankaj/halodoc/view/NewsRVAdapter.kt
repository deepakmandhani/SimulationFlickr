package com.pankaj.halodoc.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pankaj.halodoc.R
import com.pankaj.halodoc.model.Hits
import kotlinx.android.synthetic.main.row_item_news_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsRVAdapter(private val context: Context, var list: List<Hits>) : RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item_news_layout,
            parent, false))
    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Hits>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val obj = list[position]
            obj.apply {
                itemView.tv_title.text = title ?: "N/A"
                itemView.tv_author.text = author
                itemView.tv_url.text = url ?: "N/A"
                itemView.tv_createdAt.text = getFormattedDate(createdAt)
            }
        }

        fun getFormattedDate(longTime: Long): String {
            val date = Date(longTime)
            val pattern = "dd-MM-yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            return simpleDateFormat.format(date)
        }
    }
}