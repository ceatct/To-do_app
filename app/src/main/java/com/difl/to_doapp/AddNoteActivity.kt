package com.difl.to_doapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.difl.to_doapp.databinding.ActivityAddNoteBinding
import com.difl.to_doapp.databinding.ActivityMainBinding
import com.difl.to_doapp.note.NoteDao

lateinit var bindingadd : ActivityAddNoteBinding
lateinit var noteDao: NoteDao

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingadd = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(bindingadd.root)

        noteDao = NoteDao()

        bindingadd.addBt.setOnClickListener{
            val note = bindingadd.noteEdt.text.toString()
            if(note.isNotEmpty()){
                noteDao.addNote(note)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Please, add text", Toast.LENGTH_SHORT).show()
        }

    }
}