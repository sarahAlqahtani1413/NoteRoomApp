package com.example.notesapproom

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapproom.databinding.CustomNoteBinding

class NotesAdapter(
    private val activity: Activity,
    private val list: List<Note>,
    private val onClick: (data: Note, status: String) -> Unit,
) :
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: CustomNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val b: CustomNoteBinding =
            CustomNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(b)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]

        if (position%2 != 1){
            holder.binding.root.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.bg))
            holder.binding.layout.setBackgroundColor(ContextCompat.getColor(activity,R.color.bg))
        }else{
            holder.binding.root.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.white))
            holder.binding.layout.setBackgroundColor(ContextCompat.getColor(activity,R.color.white))
        }


        holder.binding.txtNote.text = data.name

        holder.binding.imgEdit.setOnClickListener {
            onClick(data,"Update")
        }

        holder.binding.imgDelete.setOnClickListener {
            onClick(data,"Delete")
        }

    }

    override fun getItemCount() = list.size

}
