package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {


    EditText et_email;
    EditText et_password;

    Button btn_register;
    Button btn_login;

    FirebaseAuth hAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login = findViewById(R.id.registerLoginButton);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        et_email = findViewById(R.id.registerEmail);
        et_password = findViewById(R.id.registerPassword);

        btn_register = findViewById(R.id.registerRegisterButton);

        hAuth = FirebaseAuth.getInstance();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "email ve şifre alanı boş bırakılamaz", Toast.LENGTH_SHORT).show();
                }
                hAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(Register.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}