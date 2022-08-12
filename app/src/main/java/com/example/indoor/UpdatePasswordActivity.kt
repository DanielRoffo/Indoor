package com.example.indoor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.indoor.databinding.ActivityUpdatePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.changePasswordAppCompatButton.setOnClickListener {
            val currentPassword = binding.currentPasswordEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()

            if (newPassword.isEmpty()){
                Toast.makeText(this, "La contraseña es debil.",
                    Toast.LENGTH_SHORT).show()
            } else if (newPassword != repeatPassword){
                Toast.makeText(this, "Confirma la contraseña.",
                    Toast.LENGTH_SHORT).show()
            } else {
                changePassword(currentPassword, newPassword)
            }
        }

    }

    private  fun changePassword(current : String, password : String){
        val user = auth.currentUser

        if (user != null){
            val email = user.email
            val credential = EmailAuthProvider
                .getCredential(email!!, current)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {

                        user.updatePassword(password)
                            .addOnCompleteListener { taskUpdatePassword ->
                                if (taskUpdatePassword.isSuccessful) {
                                    Toast.makeText(this, "Se cambio la contraseña.",
                                        Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    this.startActivity(intent)
                                }
                            }

                    } else {
                        Toast.makeText(this, "La contraseña actual es incorrecta.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }




    }
}