package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_E_P extends AppCompatActivity {

    EditText Email,Password,ConfirmPassword;
    Button ButtonNext;
    TextView Login;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_e_p);

        mFirebaseAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.editText4);
        Password = findViewById(R.id.editText8);
        ConfirmPassword = findViewById(R.id.editText9);
        ButtonNext = findViewById(R.id.button_next_signupep);
        Login = findViewById(R.id.textView_login);

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String pwd = Password.getText().toString();
                String cpwd = ConfirmPassword.getText().toString();
                if(email.isEmpty()) {
                    Email.setError("Please enter a valid email address.");
                    Email.requestFocus();
                }
                else if(pwd.isEmpty()) {
                    Password.setError("Please enter a valid password.");
                    Password.requestFocus();
                }
                else if(!pwd.equals(cpwd)) {
                    ConfirmPassword.setError("Passwords do not match.");
                    ConfirmPassword.requestFocus();
                }
                else if(!(email.isEmpty()&&pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(Signup_E_P.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(Signup_E_P.this, "Signup Failed!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(Signup_E_P.this, Dashboard.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Signup_E_P.this, "Check Again!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup_E_P.this, Login.class);
                startActivity(i);
                finish();
                return;
            }
        });
    }
}
