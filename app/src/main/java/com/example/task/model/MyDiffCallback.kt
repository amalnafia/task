package com.example.task.model

import androidx.recyclerview.widget.DiffUtil

class MyDiffCallback(private var newModel: List<Movies>, private var oldModel: List<Movies>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldModel.size
    }

    override fun getNewListSize(): Int {
        return newModel.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModel[oldItemPosition].id == newModel[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModel[oldItemPosition] == newModel[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}