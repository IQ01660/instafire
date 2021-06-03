package com.example.instafire

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instafire.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    // will contain all methods for auth with Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener{
            loginHandler(it)
        }
    }

    override fun onStart() {
        super.onStart()

        // check if the Firebase still kept the user signed in
        val currentUser = auth.currentUser
        // if yes then navigate the user to Posts screen
        if (currentUser != null) {
            goPostsActivity()
        }
    }

    /**
     * Handles to the Log In button click on Login page
     */
    private fun loginHandler(view: View) {
        // disable the log in btn
        binding.btnLogin.isEnabled = false

        val email = binding.loginEmail.text.toString()
        val pass = binding.loginPass.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this,
                "Email or Password can't be empty",
                Toast.LENGTH_LONG).
                show()
            return
        }

        // Firebase auth check here
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener{ task ->
                // re-enable the login button
                binding.btnLogin.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(this, "Successful Sign In!",
                        Toast.LENGTH_SHORT).show()
                    goPostsActivity()
                }
                else {
                    Log.w("LOG_IN", "signInWithEmailFailed", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Navigates to PostsActivity using an intent
     */
    private fun goPostsActivity() {
        Log.i("LOG_IN", "navigating to PostsActivity")
        val intent = Intent(this, PostsActivity::class.java)
        startActivity(intent)
        finish()
    }
}