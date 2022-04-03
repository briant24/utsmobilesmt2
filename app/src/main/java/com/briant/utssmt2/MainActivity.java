package com.briant.utssmt2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String strid;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tved);
        logout = findViewById(R.id.btnlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keluar = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(keluar);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Intent ambildata=getIntent();
        strid = ambildata.getStringExtra("username");
        textView.setText(strid);
    }
}