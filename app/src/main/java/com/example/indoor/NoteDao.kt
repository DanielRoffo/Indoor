package com.example.indoor

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class NoteDao {


    private val db = FirebaseFirestore.getInstance()
    val noteCollection = db.collection("Users")

    private val auth = Firebase.auth

    fun addNote(text: String, time: String){
        val currentUserId = auth.currentUser!!.uid
        val note = Note(text, currentUserId, time)
        noteCollection.document(currentUserId).collection("Notes").document().set(note)

    }
}