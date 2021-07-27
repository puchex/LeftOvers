package com.example.leftovers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.leftovers.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    private  lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener { login() }
        binding.gotoRegisterLink.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private  fun login(){
        val email = binding.loginEmail.editText?.text.toString().trim()
        val password = binding.loginPassword.editText?.text.toString().trim()
        if(email.isEmpty()){
            binding.loginEmail.error = "Email required"
            return
        }
        if(password.isEmpty()){
            binding.loginPassword.error = "Password required."
            return}

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext,"Login Failed",Toast.LENGTH_SHORT).show()
        }
    }
}