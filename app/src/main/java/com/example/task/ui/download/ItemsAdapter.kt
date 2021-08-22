package com.example.task.ui.download

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.enum.DownloadStatus
import com.example.task.interfaces.OnItemAdapterClick
import com.example.task.model.Movies
import com.example.task.model.MyDiffCallback
import kotlinx.android.synthetic.main.download_activity_item.view.*
import javax.inject.Inject


class ItemsAdapter @Inject constructor() : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    private var list: List<Movies>? = null
    private var context: Context? = null
    private var onItemAdapterClick: OnItemAdapterClick? = null

    fun setOnItemAdapterClick(onItemAdapterClick: OnItemAdapterClick?) {
        this.onItemAdapterClick = onItemAdapterClick
    }

    fun setContext(context: Context?) {
        this.context = context
    }

    fun setAdapterModel(list: List<Movies>?) {
        this.list = list
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Movies>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(newList, list!!))
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.items_activity_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.downloadIcon.setOnClickListener(this)
        }

        fun bind(movies: Movies) {
            itemView.fileName.text = movies.name
            itemView.downloadStatus.text = movies.downloadStatus.toString().lowercase()
            itemView.downloadPercentage.text = "${movies.downloadPercentage}  %"
            setItemViewVisibility(movies)
        }

        private fun setItemViewVisibility(movies: Movies) {
            when (movies.downloadStatus) {
                DownloadStatus.PENDING -> {
                    itemView.downloadIcon.visibility = View.VISIBLE
                    itemView.progressBar.visibility = View.GONE
                    itemView.downloadPercentage.visibility = View.GONE
                }
                DownloadStatus.DOWNLOADING -> {
                    itemView.progressBar.visibility = View.VISIBLE
                    itemView.downloadPercentage.visibility = View.VISIBLE
                }
                else -> {
                    itemView.progressBar.visibility = View.GONE
                    itemView.downloadPercentage.visibility = View.GONE
                    itemView.downloadIcon.visibility = View.GONE
                }
            }
        }

        override fun onClick(v: View) {
            if (v.id == R.id.downloadIcon) {
                list?.get(absoluteAdapterPosition)?.let {
                    onItemAdapterClick?.onItemAdapterClick(absoluteAdapterPosition, it.id, it.url)
                }
                notifyDataSetChanged()
            }
        }
    }
}