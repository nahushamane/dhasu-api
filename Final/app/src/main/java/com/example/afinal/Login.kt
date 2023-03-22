package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class Login : AppCompatActivity() {
    var mEmail: EditText? = null
    var mPassword: EditText? = null
    var ButtonSignIn: Button? = null
    var Signup: TextView? = null
    private var mAuth: FirebaseAuth? = null
    private var mAuthStateListner: AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        mEmail = findViewById(R.id.username)
        mPassword = findViewById(R.id.password)
        ButtonSignIn = findViewById(R.id.button_signin_login)
        Signup = findViewById(R.id.textView_signup)
        mAuthStateListner = AuthStateListener {
            val mFirebaseUser = mAuth!!.currentUser
            if (mFirebaseUser != null) {
                Toast.makeText(this@Login, "Login Successful!", Toast.LENGTH_SHORT).show()
                val i = Intent(this@Login, Dashboard::class.java)
                startActivity(i)
                finish()
                return@AuthStateListener
            }
        }
        ButtonSignIn.setOnClickListener(View.OnClickListener {
            val email = mEmail.getText().toString()
            val password = mPassword.getText().toString()
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@Login) { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            this@Login,
                            "Login Unsuccessful! Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
        Signup.setOnClickListener(View.OnClickListener {
            val i = Intent(this@Login, Signup_E_P::class.java)
            startActivity(i)
            finish()
            return@OnClickListener
        })
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthStateListner!!)
    }

    override fun onStop() {
        super.onStop()
        mAuth!!.removeAuthStateListener(mAuthStateListner!!)
    }
}