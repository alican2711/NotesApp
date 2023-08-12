package com.alicanknt.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alicanknt.todo.databinding.RowLayoutBinding
import com.alicanknt.todo.model.NoteData

class NotesAdapter:RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {
     var dataList  = emptyList<NoteData>()
    class MyViewHolder (private val binding : RowLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(noteData: NoteData){
            binding.notesData = noteData
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater =LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.MyViewHolder {
        return  MyViewHolder.from(parent)


    }

    override fun onBindViewHolder(holder: NotesAdapter.MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)




    }

    override fun getItemCount(): Int {
        return dataList.size

    }
    fun setData(noteData: List<NoteData>){
        val noteDiFffUtil = DiffUtil(noteData,dataList)
        val resultDiffUtil =androidx.recyclerview.widget.DiffUtil.calculateDiff(noteDiFffUtil)
        this.dataList = noteData
        resultDiffUtil.dispatchUpdatesTo(this)


    }
}