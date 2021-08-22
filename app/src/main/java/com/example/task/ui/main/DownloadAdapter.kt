package com.example.task.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.Movies
import kotlinx.android.synthetic.main.download_activity_item.view.*
import javax.inject.Inject


class DownloadAdapter @Inject constructor() : RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {

    private var list: List<Movies>? = null
    private var context: Context? = null

    fun setContext(context: Context?) {
        this.context = context
    }

    fun setAdapterModel(list: List<Movies>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.download_activity_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movies: Movies) {
            itemView.fileName.text = movies.name
            itemView.downloadStatus.text = movies.downloadStatus
            if (movies.downloadStatus == "Downloaded")
                itemView.progressBar.visibility = View.GONE
        }
    }
}