package com.alicanknt.todo.adapter


import androidx.recyclerview.widget.DiffUtil
import com.alicanknt.todo.model.NoteData

class DiffUtil(
    private val newList:List<NoteData>,
    private val oldList: List<NoteData>,
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size

    }

    override fun getNewListSize(): Int {
        return newList.size

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition]=== newList[newItemPosition]

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].id== newList[newItemPosition].id &&      oldList[oldItemPosition].title=== newList[newItemPosition].title &&
                  oldList[oldItemPosition].description == newList[newItemPosition].description





    }
}