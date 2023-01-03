package com.difl.to_doapp

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.difl.to_doapp.databinding.ActivityMainBinding
import com.difl.to_doapp.note.Note
import com.difl.to_doapp.note.NoteDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

lateinit var binding : ActivityMainBinding
lateinit var noteDao2: NoteDao
lateinit var auth2: FirebaseAuth
lateinit var adapter: Adapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                adapter.delete(position)
            }
        }).attachToRecyclerView(binding.recycler)

        noteDao2 = NoteDao()
        auth2 = Firebase.auth

        binding.fub.setOnClickListener{
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        setupRV()

    }

    private fun setupRV(){
        binding.recycler.layoutManager = LinearLayoutManager(this)
        val noteCollection = noteDao2.noteCollection
        val currentUserId = auth2.currentUser!!.uid

        val query = noteCollection.whereEqualTo("uid", currentUserId).orderBy("text", Query.Direction.ASCENDING)

        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note::class.java).build()

        adapter = Adapter(recyclerViewOption)
        binding.recycler.adapter = adapter
    }

    override fun onStart(){
        super.onStart()
        adapter.startListening()
    }

    override fun onStop(){
        super.onStop()
        adapter.stopListening()
    }

}
