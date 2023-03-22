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

class Signup_E_P : AppCompatActivity() {
    var Email: EditText? = null
    var Password: EditText? = null
    var ConfirmPassword: EditText? = null
    var ButtonNext: Button? = null
    var Login: TextView? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private val firebaseAuthStateListner: AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_e_p)
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
                    .addOnCompleteListener(this@Signup_E_P) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(this@Signup_E_P, "Signup Failed!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            startActivity(Intent(this@Signup_E_P, Dashboard::class.java))
                        }
                    }
            } else {
                Toast.makeText(this@Signup_E_P, "Check Again!", Toast.LENGTH_SHORT).show()
            }
        })
        Login.setOnClickListener(View.OnClickListener {
            val i = Intent(this@Signup_E_P, Login::class.java)
            startActivity(i)
            finish()
            return@OnClickListener
        })
    }
}