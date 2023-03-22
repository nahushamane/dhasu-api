package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class Signup : AppCompatActivity() {

    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var ConfirmPassword: EditText
    private lateinit var ButtonNext: Button
    private lateinit var Login: TextView

    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var firebaseAuthStateListner: AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mFirebaseAuth = FirebaseAuth.getInstance()
        Email = findViewById(R.id.editText4)
        Password = findViewById(R.id.editText8)
        ConfirmPassword = findViewById(R.id.editText9)
        ButtonNext = findViewById(R.id.button_next_signupep)
        Login = findViewById(R.id.textView_login)

        ButtonNext.setOnClickListener(View.OnClickListener {
            val email = Email.getText().toString()
            val pwd = Password.getText().toString()
            val cpwd = ConfirmPassword.getText().toString()
            if (email.isEmpty()) {
                Email.setError("Please enter a valid email address.")
                Email.requestFocus()
            } else if (pwd.isEmpty()) {
                Password.setError("Please enter a valid password.")
                Password.requestFocus()
            } else if (pwd != cpwd) {
                ConfirmPassword.setError("Passwords do not match.")
                ConfirmPassword.requestFocus()
            } else if (!(email.isEmpty() && pwd.isEmpty())) {
                mFirebaseAuth!!.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this@Signup) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(this@Signup, "Signup Failed!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            startActivity(Intent(this@Signup, Dashboard::class.java))
                        }
                    }
            } else {
                Toast.makeText(this@Signup, "Check Again!", Toast.LENGTH_SHORT).show()
            }
        })

        Login.setOnClickListener(View.OnClickListener {
            val i = Intent(this@Signup, Login::class.java)
            startActivity(i)
            finish()
            return@OnClickListener
        })
    }
}