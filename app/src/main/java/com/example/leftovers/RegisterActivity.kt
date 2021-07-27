package com.example.leftovers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.leftovers.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var mAuth : FirebaseAuth
    private  var database : DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener { register() }

        binding.gotoLoginLink.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun register(){
        val name : String = binding.registerFullName.editText?.text.toString()
        val email : String = binding.registerEmail.editText?.text.toString().trim()
        val password1 : String = binding.registerPassword1.editText?.text.toString().trim()
        val password2 : String = binding.registerPassword2.editText?.text.toString().trim()

        if(name.isEmpty()){
            binding.registerFullName.error    = "Name required"
            return
        }
        if(password1.isEmpty()){
            binding.registerEmail.error = "Password required."
            return}
        if(password1.length < 6){
            binding.registerPassword1.error = "Minimum Length is 6."
            return}
        if(password1 != password2){
            binding.registerPassword2.error = "Passwords not matched"
            return}

        mAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener { task ->
            Toast.makeText(applicationContext,"Good for now!",Toast.LENGTH_SHORT).show()
            if(task.isSuccessful){
                database = FirebaseDatabase.getInstance().reference
                val user = User(name,email)
                database!!.child("Users").child(mAuth.currentUser?.uid.toString()).setValue(user).addOnSuccessListener {
                    val intent = Intent(this,LoginActivity::class.java)
                    Toast.makeText(applicationContext,"Registration Successful! Login Now.", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                    finish()
                }
            }
        }.addOnFailureListener{
                exception ->
            Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_SHORT).show()

        }
    }
}