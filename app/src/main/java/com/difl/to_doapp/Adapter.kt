package com.difl.to_doapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.difl.to_doapp.note.Note
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class Adapter (options: FirestoreRecyclerOptions<Note>) : FirestoreRecyclerAdapter<Note, Adapter.ReViewHolder>(options) {

    fun delete(position: Int){
        snapshots.getSnapshot(position).reference.delete()
    }

    class ReViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReViewHolder {
        return ReViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ReViewHolder, position: Int, model: Note) {
        holder.txt.text = model.text
    }

}