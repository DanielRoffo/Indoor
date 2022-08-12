package com.example.indoor

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.indoor.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private val fileResult = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        auth = Firebase.auth

        updateUI()

        binding.changePassword.setOnClickListener{
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            this.startActivity(intent)
        }

        binding.deleteAccount.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            this.startActivity(intent)
        }


        binding.profileSettings.setOnClickListener {
            // Creacion de un alertDialog --> Ventana q se abre dentro de un mismo activity.
            // Se pasan los valores
            val builder = android.app.AlertDialog.Builder(this@ProfileActivity)
            val view = layoutInflater.inflate(R.layout.dialog_profile_settings, null)
            // Se pasa la vista al builder
            builder.setView(view)
            //Se crea el Dialog
            val dialog = builder.create()
            dialog.show()

            //Carga de codigo de activacion del dispositivo
            dialog.findViewById<Button>(R.id.changeUsername).setOnClickListener {

                val name =dialog.findViewById<EditText>(R.id.editUsername).text.toString()

                updateProfile(name)

                dialog.dismiss()
            }
            dialog.findViewById<Button>(R.id.changeImage).setOnClickListener{
                fileManager()
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.cancelBtn).setOnClickListener{ dialog.dismiss() }

        }

        updateUI()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private  fun updateUI () {
        val user = auth.currentUser

        if (user != null){
            binding.email.text = user.email

            if(user.displayName != ""){
                binding.username.text = user.displayName
            }

            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.icons8_male_user_48)
                .into(binding.profileImage)

        }

    }

    private  fun updateProfile (name : String) {

        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Changes were made correctly.",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data

                uri?.let { imageUpload(it) }

            }
        }
    }

    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Changes were made correctly.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }


}