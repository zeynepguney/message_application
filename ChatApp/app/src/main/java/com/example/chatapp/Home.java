
package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity {
    FirebaseAuth hAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.button_register).setOnClickListener(view -> {
            startActivity(new Intent(Home.this,Register.class));
        });
        findViewById(R.id.button_login).setOnClickListener(view -> {
            startActivity(new Intent(Home.this,Login.class));
        });
        hAuth = FirebaseAuth.getInstance();
        if (hAuth.getCurrentUser() != null){
            Toast.makeText(this, "Anasayfaya geçiş yapılıyor.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Home.this, MainActivity.class));
        }
    }
}