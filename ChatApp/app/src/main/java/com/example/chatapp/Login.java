package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    EditText et_email;
    EditText et_password;

    Button btn_login;
    Button btn_register;

    FirebaseAuth hAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_register = findViewById(R.id.loginRegisterButton);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        et_email = findViewById(R.id.loginEmail);
        et_password = findViewById(R.id.loginPassword);

        btn_login = findViewById(R.id.loginLoginButton);

        hAuth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "email ve şifre alanı boş bırakılamaz", Toast.LENGTH_SHORT).show();
                }
                    hAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                        }
                        else {
                            Toast.makeText(Login.this, "Giriş Başarısız", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }
}