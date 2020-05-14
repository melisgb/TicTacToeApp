package com.ggonzales.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {
    //Authentication
    private var mAuth : FirebaseAuth? = null
    //DB
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        mAuth = FirebaseAuth.getInstance()

        signinButton.setOnClickListener {
            val email = emailEText.text.toString()
            val pass = pswEText.text.toString()
            loginToFireBase(email, pass)
        }

    }
    fun loginToFireBase( email: String, pass : String){
        mAuth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    Log.d("Firebase Log", "Login successful")
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val user = mAuth!!.currentUser
                    loadMainActiv(user!!)
                }
                else {
                    Log.d("Firebase Log", "Login failed")
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
    fun loadMainActiv(currentUser : FirebaseUser){
        //save into FirebaseDB
        myRef.child("users").child(currentUser.uid).setValue(currentUser.email)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("Email", currentUser.email)
        intent.putExtra("UserID", currentUser.uid)
        startActivity(intent)

    }

    override fun onStart() {
        super.onStart()
        val user = mAuth!!.currentUser
        if(user!= null){
            loadMainActiv(user)
        }

    }
}
