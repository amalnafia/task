package com.example.task.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.Movie
import kotlinx.android.synthetic.main.main_activity_item.view.*
import javax.inject.Inject


class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var list: List<Movie>? = null
    private var context: Context? = null

    fun setContext(context: Context?) {
        this.context = context
    }

    fun setAdapterModel(list: List<Movie>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.main_activity_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.fileName.text = movie.name
            itemView.downloadStatus.text = movie.downloadStatus
            if (movie.downloadStatus == "Downloaded")
                itemView.progressBar.visibility = View.GONE
        }
    }
}