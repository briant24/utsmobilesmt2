package com.briant.utssmt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout tvemail,tvpassword;
    String email,password;
    Button btnlogin, btnregis;
    DatabaseReference firebaseDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvemail = findViewById(R.id.txtemail);
        tvpassword = findViewById(R.id.txtpassword);
        progressBar = findViewById(R.id.progressBar2);
        btnlogin = findViewById(R.id.btn_login);
        btnregis = findViewById(R.id.btn_registrasi);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrasi = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registrasi);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
    }

    private void validasi() {
        email = tvemail.getEditText().getText().toString();
        password = tvpassword.getEditText().getText().toString();
        if (email.isEmpty()){
            tvemail.requestFocus();
        }
        else if (password.isEmpty()){
            tvpassword.requestFocus();
        }
        else {
            firebaseDatabase.child("Users")
                    .orderByChild("username")
                    .equalTo(email)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String pass = data.child("password").getValue().toString();
                                String user = data.child("username").getValue().toString();
                                if (pass.equals(password) && user.equals(email)) {
                                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("username", email);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Username dan atau password salah", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }
}