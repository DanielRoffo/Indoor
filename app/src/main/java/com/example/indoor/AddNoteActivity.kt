package com.example.indoor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.indoor.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var noteEditText = binding.noteEditText
        var addNoteBnt = binding.addNoteBtn
        var noteDao = NoteDao()

        addNoteBnt.setOnClickListener {
            val note = noteEditText.text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            if (note.isNotEmpty()){

                noteDao.addNote(note, currentDate)

                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}