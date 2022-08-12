package com.example.indoor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.indoor.databinding.ActivityNotesBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var noteDao: NoteDao
    private lateinit var adaptor: NotesAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        auth = Firebase.auth
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        recyclerView = binding.recyclerView
        var fab = binding.fab

        noteDao = NoteDao()
        auth = Firebase.auth

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            this.startActivity(intent)
        }

        fab.setOnClickListener{
            val intent = Intent(this, AddNoteActivity::class.java)
            finish()
            startActivity(intent)
        }

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteCollection = noteDao.noteCollection
        val currentUserId = auth.currentUser!!.uid
        val query = noteCollection.document(currentUserId).collection("Notes").whereEqualTo("uid", currentUserId).orderBy("time", Query.Direction.DESCENDING)
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note::class.java).build()
        adaptor = NotesAdapter(recyclerViewOption)
        recyclerView.adapter = adaptor


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                adaptor.deleteNote(position)
                val intent = Intent(this@NotesActivity, NotesActivity::class.java)
                finish()
                startActivity(intent)
                //adaptor.notifyItemRemoved(position)

            }


        }).attachToRecyclerView(recyclerView)
    }

    override fun onStart() {
        super.onStart()
        adaptor.startListening()
    }
    override fun onStop(){
        super.onStop()
        adaptor.stopListening()
    }
}