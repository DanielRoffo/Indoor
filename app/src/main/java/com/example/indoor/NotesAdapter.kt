package com.example.indoor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NotesAdapter(options: FirestoreRecyclerOptions<Note>): FirestoreRecyclerAdapter<Note, NotesAdapter.RVViewHolder>(options) {

    class RVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val noteText: TextView = itemView.findViewById(R.id.noteText)
        val noteTime: TextView = itemView.findViewById(R.id.noteTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        return RVViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int, model: Note) {
        holder.noteText.text = model.text
        holder.noteTime.text = model.time
    }

    fun deleteNote(position: Int){
        snapshots.getSnapshot(position).reference.delete()
    }
}